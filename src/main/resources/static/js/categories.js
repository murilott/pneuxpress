var btnSalvar = document.querySelector("#btnSalvar");
var listaProd2 = JSON.parse(localStorage.getItem("listaProd"));



btnSalvar.addEventListener("click", (event) => {
  event.preventDefault();

  var myform = document.querySelector("#myform");

  var CatName = myform.CatName.value;
  var catName = CatName.trim().replace(/\s+/g, " ");

  let tax = myform.tax.value;
  let tax2 = parseFloat(tax);
  let taxx = tax2.toFixed(2);
  var nomeMin = CatName.toLowerCase();



  if (nomeMin.length >= 26) {
    window.alert(
      "Maximum character size exceeded. Category name must have a maximum of 25 characters "
    );
  } else {
      if (
        catName == "" ||
        taxx == "" ||
        taxx < 0 ||
        taxx > 100 ||
        myform.tax.value == "" ||
        myform.tax.value == " " ||
        isNaN(myform.tax.value) ||
        catName == " "
      ) {
        alert("Preencha todos os Campos CORRETAMENTE");
      } else {
        var newCategory = {
          // id: code,
          nome: catName,
          // nomeMin: nomeMin,
          tax: taxx,
        };

        fetchPost(catName, taxx);
        getData();

        window.location.reload();
        myform.reset();

      }
    }
  }
);

async function fetchPost(catName, taxx) {
  try {
    const res = await fetch("http://localhost/categories", {
      method: "POST",
      body: JSON.stringify({
        nome: catName,
        tax: taxx,
      }),
    });

    if (!res.ok) {
      throw new Error("Could not Fetch");
    }


  } catch (error) {
    // console.error(error);
  }
}

async function fetchDelete(ida) {
  try {
    const res = await fetch("http://localhost/categories", {
      method: "POST",
      body: JSON.stringify({
        ID_TO_CONSULT: ida
      })
    })
      if (!res.ok) {
        throw new Error("Could not Fetch");
      }
  } catch (error) {
    console.error(error);
    console.log("não deletou");
    
  }
  
}


function deleteCategory(ida) {
  if (ValDelete(ida)) {
    window.alert(
      "This category is currently ATTATCHED to a product. You Cannot Delete this"
    );
  } else {
    console.log(ida);
    fetchDelete(ida);
    location.reload()
  }
}

function ValDelete(ida) {
  const matched = listaProd2.some((e) => e.CatID == ida);
  return matched;
}

async function getData() {
  const data = await fetch("http://localhost/categories");

  const records = await data.json();
  
  var btDelete = document.createElement("button");
  btDelete.textContent = "Delete";

  let tab = "";
  records.forEach(function (e) {
    addcat(e.code,e.nome,e.tax);
  });
  document.getElementById("tbody").innerHTML = tab;
}

function addcat(code, nome, tax) {


  var linhaCategoria = document.createElement("tr");

  var btDelete = document.createElement("button");
  btDelete.textContent = "Delete";

  btDelete.addEventListener("click", function () {
    deleteCategory(code);
  });

  var cellId = document.createElement("td");
  var cellCatName = document.createElement("td");
  var cellTax = document.createElement("td");
  var cellDelete = document.createElement("td");

  cellId.textContent = code;
  cellCatName.textContent = nome;
  cellTax.textContent = tax + "%";
  cellDelete = btDelete;

  linhaCategoria.appendChild(cellId);
  linhaCategoria.appendChild(cellCatName);
  linhaCategoria.appendChild(cellTax);
  linhaCategoria.appendChild(cellDelete);

  tabelaCategoria.appendChild(linhaCategoria);
}

window.onload = function () {
  getData();
};

//VALIDAÇÃO

//INPUTS
myform.tax.oninput = () => {
  let categoriesTaxValueRegex = myform.tax.value.replace(/[^\d.]/g, "");
  myform.tax.value = categoriesTaxValueRegex;
};

myform.CatName.oninput = () => {
  let productNameRegex = myform.CatName.value.replace(/[^a-zA-Z]/g, "");
  myform.CatName.value = productNameRegex;
};
