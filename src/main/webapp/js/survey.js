var cnt = 0;

function createProduct() {

    var container = document.getElementById("survey-gallery");
    var productName = document.getElementById("product-name");
    var productInfo = document.getElementById("product-info");

    function createDiv(className, parent) {
        var div = document.createElement("div");

        if (className)
            div.className = className;

        if (parent)
            parent.appendChild(div);

        return div;
    }

    function createParagraph(className, innerHTML, parent) {
        var p = document.createElement("p");

        if (className)
            p.className = className;

        if (innerHTML)
            p.innerHTML = innerHTML;

        if (parent)
            parent.appendChild(p);

        return p;
    }

    function createInput(className, name, parent) {
        var input = document.createElement("input");

        if (className)
            input.className = className;

        if (parent)
            parent.appendChild(input);

        if (name)
            input.name = name;

        return input;
    }

    function createCanvas(id, parent) {
        var canvas = document.createElement("canvas");

        if (parent)
            parent.appendChild(canvas);

        if (id)
            canvas.id = id;

        canvas.width = 0;
        canvas.height = 0;
        return canvas;
    }

    function removeHint() {
        var gallery = document.getElementById("survey-gallery");
        if (gallery.hasChildNodes()) {
            document.getElementById("add-hint").innerHTML = '';
        }
    }

    function createNewRow() {
        var rowDivClassName = "row gallery-row";
        var colParClassName = "preview-col col-md-4";
        var imgDivClassName = colParClassName + " upload-img-div";
        var imgInputClassName = "img-input";
        var imgInputName = "img-" + cnt;
        var nameInputName = "product-" + cnt;
        var canvasId = "canvas-" + cnt;

        var row = createDiv(rowDivClassName, container);
        createParagraph(colParClassName, productName.value, row);
        createParagraph(colParClassName, productInfo.value, row);
        var imgDiv = createDiv(imgDivClassName, row);
        imgDiv.innerHTML = "Choose an image";
        createCanvas(canvasId, imgDiv);

        var imgInput = createInput(imgInputClassName, imgInputName, imgDiv);
        imgInput.type = "file";
        imgInput.addEventListener("change", handleImage, false);

        var nameInput = createInput("", nameInputName, document.getElementById("survey"));
        nameInput.type = "hidden";
        nameInput.value = productName.value;

        cnt += 1;

        productInfo.value = '';
        productName.value = '';
    }


    if (productName.value)
        createNewRow();

    removeHint();
}

function handleImage(e) {


    var name = e.target.name;
    var id = name.replace("img-", "");
    var canvas = document.getElementById("canvas-" + id);
    document.getElementsByName("img-" + id)[0].style.display = "none";
    e.target.parentNode.style.color = "#F3F3F1";

    var reader = new FileReader();
    reader.onload = function (event) {
        var img = new Image();
        img.onload = function () {
            var ctx = canvas.getContext('2d');
            canvas.width = 220;
            canvas.height = 250;
            ctx.drawImage(img, 0, 0, 220, 250);
        }
        img.src = event.target.result;
    }
    reader.readAsDataURL(e.target.files[0]);
}