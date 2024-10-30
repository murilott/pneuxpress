var btnSalvar = document.querySelector("#subb");
var categoriaSelect = document.querySelector("#categoria");
var listaCat2 = localStorage.listaCat2;
var listaCat2 = JSON.parse(localStorage.getItem("listaCat2"));
var listaCart = JSON.parse(localStorage.getItem("listaCart"));

//tem que tirar isso ai...
var listaProd = []


getDataOption();

async function getDataOption() {
  const data = await fetch("http://localhost/categories");

  const records = await data.json();
  
  records.forEach((opt) => {
    let option = document.createElement("option");
    option.text = opt.nome;
    option.value = opt.code;
  
    categoriaSelect.append(option);
  });
}

btnSalvar.addEventListener("click", (event) => {
  event.preventDefault();

  var myform = document.querySelector("#myform");
  var catID = myform.categoria.value;


  var UltiCode = listaProd.at(-1)?.idProd ?? 0;
  var code = UltiCode + 1;

  var ProdName = myform.prodname.value;
  var prodName = ProdName.trim().replace(/\s+/g, " ");

  let amount = myform.amount.value;
  let Amount = parseInt(amount);
  
  
  let unitp = myform.unitprice.value;
  let Unitp = parseFloat(unitp);
  let Unitp2 = Unitp.toFixed(2);

  const sellProduct = document.getElementById("categoria").value;
  const selectedOptionE = document.querySelector(
    "#categoria > option[value=" + CSS.escape(sellProduct) + "] "
  );

  const catName2 = selectedOptionE.text;
  

  var nomeMin = prodName.toLowerCase();

  function ValNome(nome){
    const matched = listaProd.some( e => e.prodnameMin == nome);
    return matched
  }
  
  // const nome = listaProd.prodname;
  // console.log(nome);
  
  if(nomeMin.length >= 51){
    window.alert("Maximum character size exceeded. Product name must have a maximum of 50 characters ")
  }else{
  if(
    ValNome(nomeMin)
  ){
    window.alert("The system already have a product with that name, you must change the name or delete the old one")
  }else{
  if (
    myform.prodname.value == "" ||
    myform.prodname.value == " " ||
    myform.amount.value == "" ||
    myform.unitprice == "" ||
    isNaN(myform.unitprice.value)||
    myform.CatID == ""
  ) {
    alert("Preencha todos os Campos, CORRETAMENTE");
  } else {
    var newProduct = {
      idProd: code,
      prodname: prodName,
      prodnameMin: nomeMin,
      amount: Amount,
      unitprice: Unitp2,
      CatName: catName2,
      CatID: catID,
    };
    
    fetchPost(prodName, Unitp2, Amount, myform.categoria.value);
    getData();
    location.reload();
  }
}}});

async function getData() {
  const data = await fetch("http://localhost/products");

  const records = await data.json();
  
  records.forEach(function (e) {
    addProd(e.code, e.nome, e.price, e.amount, e.category_code);
  });

}

async function fetchPost(prodName, Unitp2, Amount, catID) {
  try {
    const res = await fetch("http://localhost/products", {
      method: "POST",
      body: JSON.stringify({
        nome: prodName,
        price: Unitp2,
        amount: Amount,
        category_code: catID,
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
    const res = await fetch("http://localhost/products", {
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


// function ValDelete(ida) {
//   const matched = listaCart.some( e => e.idProd == ida);
//   return matched;
// }

function deleteProd(ida) {

  console.log(ida);
  fetchDelete(ida);
  location.reload()
}

function addProd(code, nome, price, amount, category_code) {
  var linhaProd = document.createElement("tr");

  var btDelete = document.createElement("button");
  btDelete.textContent = "Delete";

  btDelete.addEventListener("click", function () {
    deleteProd(code);
  });

  var cellIdProd = document.createElement("td");
  var cellProdName = document.createElement("td");
  var cellAmount = document.createElement("td");
  var cellUnitPrice = document.createElement("td");
  var cellCatID = document.createElement("td");
  var cellDelete = document.createElement("td");

  cellIdProd.textContent = code;
  cellProdName.textContent = nome;
  cellAmount.textContent = amount;
  cellUnitPrice.textContent = "R$" + price;
  cellCatID.textContent = category_code;
  cellDelete = btDelete;

  var tabelaProduto = document.querySelector("#tabelaProduto");

  linhaProd.appendChild(cellIdProd);
  linhaProd.appendChild(cellProdName);
  linhaProd.appendChild(cellAmount);
  linhaProd.appendChild(cellUnitPrice);
  linhaProd.appendChild(cellCatID);

  linhaProd.appendChild(cellDelete);

  tabelaProduto.appendChild(linhaProd);
}

window.onload = function () {
  getData()
};




//validação

myform.amount.oninput = () => {
  let categoriesTaxValueRegex = myform.amount.value.replace(/[^\d]/g, "");
  myform.amount.value = categoriesTaxValueRegex;
};

myform.unitprice.oninput = () => {
  let categoriesTaxValueRegex = myform.unitprice.value.replace(/[^\d.]/g, "");
  myform.unitprice.value = categoriesTaxValueRegex;
};

myform.prodname.oninput = () => {
  let productNameRegex = myform.prodname.value.replace(/[^a-zA-Z 1-9-]/g, "").replace(/^\s+|\s+$/g,"");
  myform.prodname.value = productNameRegex;
};

myform.prodname.oninput = () => {
  let productNameRegex = myform.prodname.value.replace(/^\s+|\s+$/s, " ");
  myform.prodname.value = productNameRegex;
};