/* const express = require("express");
const path = require("path");

const app = express();
const PORT = 3000;

app.use(express.static(path.join(__dirname, "public")));

app.get("*splat", (req, res) => {
    res.sendFile(path.join(__dirname, "public", "index.html"));
});

app.listen(PORT, () => {
    console.log(`PollTaker frontend: http://localhost:${PORT}`);
});
*/

const express = require('express');
const app = express();
const port = 3000;

app.set('view engine', 'ejs');
app.use(express.static('public'));
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

const mockData = {
    enquetesAbertas: [
        { id: 1, titulo: "Qual framework front-end?", status: "ABERTA", votos: 150 },
        { id: 2, titulo: "Trabalho Remoto ou Presencial?", status: "ABERTA", votos: 89 }
    ],
    historicoUsuario: [
        { id: 3, titulo: "Horário de Reunião", status: "FINALIZADA", vencedor: "14h00" }
    ]
};

app.get('/', (req, res) => res.render('index', { enquetes: mockData.enquetesAbertas }));
app.get('/login', (req, res) => res.render('login'));
app.get('/dashboard', (req, res) => res.render('dashboard'));
app.get('/dashboard/nova', (req, res) => res.render('nova'));
app.get('/dashboard/ativas', (req, res) => res.render('ativas', { ativas: mockData.enquetesAbertas }));
app.get('/dashboard/historico', (req, res) => res.render('historico', { historico: mockData.historicoUsuario }));

app.listen(port, () => console.log(`Servidor rodando em http://localhost:${port}`));