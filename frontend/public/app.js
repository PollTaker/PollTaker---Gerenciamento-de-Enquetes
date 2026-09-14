const API = "http://localhost:8080/api/v1";

const $ = (id) => document.getElementById(id);

function showMessage(text, error = false) {
  const el = $("message");
  el.textContent = text;
  // Remove as classes anteriores e adiciona a nova baseada no status
  el.className = `message ${error ? "error" : "success"}`;
  setTimeout(() => {
    el.classList.remove("error", "success");
    el.style.display = "none";
  }, 4500);
  el.style.display = "block"; // Garante que a mensagem apareça
}

async function request(url, options = {}) {
  const response = await fetch(`${API}${url}`, {
    headers: { "Content-Type": "application/json", ...(options.headers || {}) },
    ...options
  });

  if (!response.ok) {
    let detail = `Erro HTTP ${response.status}`;
    try {
      const body = await response.json();
      detail = body.detail || body.message || detail;
      if (body.invalid_params) {
        detail += " " + body.invalid_params.map(p => `${p.name}: ${p.reason}`).join(" | ");
      }
    } catch (_) {}
    throw new Error(detail);
  }

  if (response.status === 204) return null;
  return response.json();
}

async function checkApi() {
  try {
    await request(""); // Request para a raiz da API para verificar o status
    $("apiDot").style.background = "#16a34a"; // Verde
    $("apiStatus").textContent = "API online";
  } catch (e) {
    $("apiDot").style.background = "#dc2626"; // Vermelho
    $("apiStatus").textContent = "API offline";
  }
}

async function loadPolls() {
  try {
    const polls = await request("/enquetes");
    $("pollCount").textContent = polls.length;

    let optionCount = 0;
    polls.forEach(p => optionCount += (p.opcoes || []).length);
    $("optionCount").textContent = optionCount;

    const list = $("pollList");
    list.innerHTML = "";

    if (!polls.length) {
      list.innerHTML = "<p class='meta'>Nenhuma enquete cadastrada.</p>";
    }

    polls.forEach(poll => {
      const card = document.createElement("article");
      card.className = "poll";
      card.innerHTML = `
                <h3>${escapeHtml(poll.titulo)}</h3>
                <div class="meta">Criada por ${escapeHtml(poll.criadorNome || "usuário")} • Status: ${poll.status}</div>
                <div class="option-list">
                    ${(poll.opcoes || []).map(o => `<span class="option">${escapeHtml(o.titulo)}</span>`).join("")}
                </div>
            `;
      list.appendChild(card);
    });

    populatePollSelects(polls);
  } catch (e) {
    showMessage(e.message, true);
  }
}

function populatePollSelects(polls) {
  ["votePoll", "optionPoll"].forEach(id => {
    const select = $(id);
    select.innerHTML = '<option value="" disabled selected>Selecione uma enquete</option>' +
        polls.map(p => `<option value="${p.id}">${escapeHtml(p.titulo)}</option>`).join("");
  });

  updateVoteOptions(polls);
}

async function updateVoteOptions(pollsCache = null) {
  const pollId = Number($("votePoll").value);
  if (!pollId) {
    $("voteOption").innerHTML = '<option value="" disabled selected>Selecione uma enquete primeiro</option>';
    return;
  }

  try {
    const polls = pollsCache || await request("/enquetes");
    const poll = polls.find(p => p.id === pollId);
    $("voteOption").innerHTML = '<option value="" disabled selected>Selecione uma opção</option>' +
        (poll?.opcoes || []).map(o => `<option value="${o.id}">${escapeHtml(o.titulo)}</option>`).join("");
  } catch (e) {
    showMessage(e.message, true);
  }
}

$("votePoll").addEventListener("change", () => updateVoteOptions());

$("userForm")?.addEventListener("submit", async (event) => {
  event.preventDefault();

  const nome = $("userName").value;
  const email = $("userEmail").value;

  try {
    const novoUsuario = await request("/usuarios", {
      method: "POST",
      body: JSON.stringify({ nome, email })
    });

    // Salva a sessão do usuário logado no navegador
    localStorage.setItem("polltaker_user_id", novoUsuario.id);
    localStorage.setItem("polltaker_user_name", novoUsuario.nome || nome);

    showMessage(`Usuário cadastrado com sucesso! Redirecionando...`);
    event.target.reset();

    // Redireciona para o Mission Control após 1,5 segundos
    setTimeout(() => {
      window.location.href = "/dashboard";
    }, 1500);

  } catch (e) {
    showMessage(e.message, true);
  }
});

$("pollForm").addEventListener("submit", async (event) => {
  event.preventDefault();

  try {
    const date = $("pollEnd").value;
    await request("/enquetes", {
      method: "POST",
      body: JSON.stringify({
        titulo: $("pollTitle").value,
        criadorId: Number($("creatorId").value),
        dataEncerramento: date.length === 16 ? `${date}:00` : date,
        status: "ABERTA"
      })
    });

    event.target.reset();
    showMessage("Enquete criada com sucesso.");
    await loadPolls();
  } catch (e) {
    showMessage(e.message, true);
  }
});

$("optionForm").addEventListener("submit", async (event) => {
  event.preventDefault();

  try {
    await request("/opcoes", {
      method: "POST",
      body: JSON.stringify({
        titulo: $("optionTitle").value,
        enqueteId: Number($("optionPoll").value)
      })
    });

    event.target.reset();
    showMessage("Opção adicionada com sucesso.");
    await loadPolls();
  } catch (e) {
    showMessage(e.message, true);
  }
});

$("voteForm").addEventListener("submit", async (event) => {
  event.preventDefault();

  try {
    await request("/votos", {
      method: "POST",
      body: JSON.stringify({
        idUsuario: Number($("voteUserId").value),
        idEnquete: Number($("votePoll").value),
        idOpcao: Number($("voteOption").value)
      })
    });

    showMessage("Voto registrado com sucesso.");
    event.target.reset();
    await loadStats();
  } catch (e) {
    showMessage(e.message, true);
  }
});

async function loadStats() {
  try {
    const votes = await request("/votos");
    $("voteCount").textContent = votes.length;
  } catch (_) {}
}

function escapeHtml(value) {
  return String(value ?? "")
      .replaceAll("&", "&amp;")
      .replaceAll("<", "&lt;")
      .replaceAll(">", "&gt;")
      .replaceAll('"', "&quot;")
      .replaceAll("'", "&#039;");
}

(async function init() {
  await checkApi();
  await loadPolls();
  await loadStats();
})();