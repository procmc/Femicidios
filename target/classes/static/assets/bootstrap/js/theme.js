const getStoredTheme = () => localStorage.getItem('icon')
const setStoredTheme = (icono) => localStorage.setItem('icon', icono);

function changeIcon(iconoClase) {
    let iconoPrincipal = document.getElementById('theme-main-icon-link');
    console.log(iconoPrincipal);
    iconoPrincipal.setAttribute('class', `fas ${iconoClase} mb-1`)
    setStoredTheme(`${iconoClase}`)
}

document.addEventListener("DOMContentLoaded", function () {

    !getStoredTheme() ? changeIcon('fa-sun') : changeIcon(getStoredTheme());
})