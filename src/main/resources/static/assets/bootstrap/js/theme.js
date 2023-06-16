function changeIcon(iconoClase) {
    let iconoPrincipal = document.getElementById('theme-main-icon-link');
    iconoPrincipal.setAttribute('class', `fas ${iconoClase} mb-1`)
    console.log(iconoPrincipal);
}

let tema = "fas-sun";