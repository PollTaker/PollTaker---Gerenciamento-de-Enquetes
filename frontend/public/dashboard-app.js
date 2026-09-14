const API_URL = "http://localhost:8080/api/v1";

document.addEventListener("DOMContentLoaded", async () => {
    const userId = localStorage.getItem("polltaker_user_id");
    const userName = localStorage.getItem("polltaker_user_name");

    if (!userId) {
        window.location.href = "/";
        return;
    }

    document.getElementById("welcomeTitle").textContent = `Olá, ${userName}`;
    document.getElementById("userSessionInfo").textContent = `ID do Usuário: #${userId}`;

    await loadUserPolls(userId);

    document.getElementById("logoutBtn").addEventListener("click", (e) => {
        e.preventDefault();
        localStorage.removeItem("polltaker_user_id");
        localStorage.removeItem("polltaker_user_name");
        window.location.href = "/";
    });
});

async function loadUserPolls(userId) {
    try {
        const response = await fetch(`${API_URL}/enquetes`);
        if (!response.ok) throw new Error("Erro ao buscar enquetes.");

        const polls = await response.json();

        const userPolls = polls.filter(p => p.criadorId === Number(userId) || p.criadorNome === localStorage.getItem("polltaker_user_name"));

        const container = document.getElementById("myPollsList");
        container.innerHTML = "";

        if (userPolls.length === 0) {
            container.innerHTML = `<p style="color: var(--text-muted); font-size: 0.9rem;">Você ainda não criou nenhuma enquete.</p>`;
            document.getElementById("engagementRate").textContent = "0%";
            return;
        }

        userPolls.forEach(poll => {
            const statusClass = poll.status === "ABERTA" ? "aberta" : "encerrada";
            const item = document.createElement("div");
            item.className = "poll-item";
            item.innerHTML = `
                <span class="badge ${statusClass}">${poll.status}</span>
                <strong style="display: block; font-size: 1rem; color: #fff; margin-bottom: 4px;">${poll.titulo}</strong>
                <span style="font-size: 0.8rem; color: var(--text-muted);">Opções cadastradas: ${(poll.opcoes || []).length}</span>
            `;
            container.appendChild(item);
        });

        document.getElementById("engagementRate").textContent = `${userPolls.length * 100}%`;

    } catch (error) {
        console.error(error);
        document.getElementById("myPollsList").innerHTML = `<p style="color: #ef4444; font-size: 0.9rem;">Erro ao carregar dados do servidor.</p>`;
    }
}