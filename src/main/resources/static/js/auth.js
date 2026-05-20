// Utilidades de autenticación compartidas entre todas las páginas

// Verifica que el usuario esté logueado y devuelve su info
async function verificarSesion() {
    try {
        const r = await fetch('/api/auth/me', { credentials: 'include' });
        if (!r.ok) {
            window.location.href = '/login.html';
            return null;
        }
        return await r.json();
    } catch {
        window.location.href = '/login.html';
        return null;
    }
}

// Rellena el navbar con usuario y botón de logout
function pintarNavbar(usuario) {
    const nb = document.getElementById('nav-info');
    if (!nb) return;
    const clsBadge = usuario.rol === 'ADMIN' ? 'badge-admin' : 'badge-user';
    nb.innerHTML = `
        <span class="text-white me-2">Hola, <strong>${usuario.username}</strong></span>
        <span class="badge ${clsBadge} me-3">${usuario.rol}</span>
        <a href="/dashboard.html" class="btn btn-outline-light btn-sm me-2">← Dashboard</a>
        <button class="btn btn-outline-danger btn-sm" onclick="cerrarSesion()">Salir</button>
    `;
}

function cerrarSesion() {
    fetch('/api/auth/logout', { method: 'POST', credentials: 'include' })
        .then(() => window.location.href = '/login.html?logout=true')
        .catch(() => window.location.href = '/login.html');
}

// Oculta botones de editar/eliminar si el usuario es USER
function aplicarPermisos(rol) {
    if (rol !== 'ADMIN') {
        document.querySelectorAll('.solo-admin').forEach(el => el.remove());
    }
}
