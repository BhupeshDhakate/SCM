console.log("Script Loaded")

let currentTheme= getTheme();
// initial -->

document.addEventListener('DOMContentLoaded', () =>{chengeTheme(currentTheme);})

// Todo
function chengeTheme(currentTheme){
    
    // set to web page
    document.querySelector('html').classList.add(currentTheme);

    // set the listener to change theme botton
    const chengeThemeButton = document.querySelector("#theme_chenge_botton");
    chengeThemeButton.querySelector('span').textContent = 
    currentTheme =='ligth'?'Dark':'light';
 
    chengeThemeButton.addEventListener("click",(event) => {
        console.log("chenge theme botton clicked");
        const oldTheme = currentTheme;
        if(currentTheme == "dark"){
            // Theme to light
            currentTheme="light";
        }else{
            // theme to dark
            currentTheme="dark";
        }
        // update in local storage
        setTheme(currentTheme);
        // remove the current theme
        document.querySelector('html').classList.remove(oldTheme);
        // set the current theme
        document.querySelector('html').classList.add(currentTheme);

        // chenge the text of botton
        chengeThemeButton.querySelector('span').textContent = currentTheme =='ligth'?'Dark':'light';
    });
}

// set theme to local storage
function setTheme(theme){
    localStorage.setItem("theme", theme);
}

// get theme from local storage
function getTheme(){
    let theme = localStorage.getItem("theme");
    return theme ? theme : "light";
}
