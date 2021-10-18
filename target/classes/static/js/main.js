console.log("Javascript it's uploaded");
let nav = document.getElementById('navbar');
nav.classList.add("navbar-transparent");

document.addEventListener('scroll', () => {
    if (document.body.scrollTop > 50 || document.documentElement.scrollTop > 50) {
        nav.classList.remove("navbar-transparent");
    } else {
         nav.classList.add("navbar-transparent");
    }
});