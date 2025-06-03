console.log("adim user");
document
.querySelector("#image_file_input")
.addEventListener('change',function(event){

    let file = event.target.files[0];
    let reader = new FileReader();
    reader.onload = function(){
        // document.getElementById("upload_imgae_preview").src = reader.result; //or
        document.querySelector('#upload_image_preview').setAttribute("src", reader.result);
    };
    reader.readAsDataURL(file);
});