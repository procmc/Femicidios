function ocultarError() {
    var errorMessage = document.getElementById("error-message");
    if (errorMessage) {
        setTimeout(function () {
            errorMessage.style.display = "none";
        }, 3000)
        //errorMessage.style.display = "none";
    }
}
