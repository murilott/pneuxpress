var productSelect = document.querySelector("#Product");
var listaProd2 = JSON.parse(localStorage.getItem("listaProd"));
var listaCat2 = JSON.parse(localStorage.getItem("listaCat2"));

if (!localStorage.getItem("listaCart")) {
  localStorage.setItem("listaCart", JSON.stringify([]));
}
if (!localStorage.getItem("listaHist")) {
  localStorage.setItem("listaHist", JSON.stringify([]));
  location.reload();
}

var listaProd = JSON.parse(localStorage.getItem("listaProd"));
var listaCart = JSON.parse(localStorage.getItem("listaCart"));
var listaHist = JSON.parse(localStorage.getItem("listaHist"));

// Mostra na tabela itens do carrinho ou Cria um novo
async function getData() {
  const data = await fetch("http://localhost/cart");

  const records = await data.json();
  
  records.forEach(function (e) {
    addCart(e.codeorder, e.nomeitem, e.amountitem, e.priceitem, e.tax);
  });
}

// CRIA AS OPÇÕES DO SELECT
getDataOption();

async function getDataOption() {
  const data = await fetch("http://localhost/products");

  const records = await data.json();
  
  records.forEach((opt) => {
    let option = document.createElement("option");
    option.text = opt.nome;
    option.value = opt.code;
  
    productSelect.append(option);
  });
}

// MANDA PRO LOCALSTORAGE
btnSalvar.addEventListener("click", (event) => {
  event.preventDefault();

  var myform = document.querySelector("#myform");

  var UltiCode = listaCart.at(-1)?.idLinha ?? 0;
  var code = UltiCode + 1;

  let amount2 = myform.amount.value;
  let Amount = parseInt(amount2.trim().replace(/\s+/g, " "));

  const sellProduct = document.getElementById("Product").value;
  const selectedOptionE = document.querySelector(
    "#Product > option[value=" + CSS.escape(sellProduct) + "] "
  );

  const productName = selectedOptionE.text;
  const productVal = selectedOptionE.value;

  // const product = listaProd.find((e) => e.idProd == sellProduct);

  //GERENCIA O ESTOQUE
  const currentInCart = listaCart
    .filter((p) => p.idProd == sellProduct)
    .reduce((x, p) => x + p.amount, 0);

  const totalAfterAdd = currentInCart + Amount;

  // if (totalAfterAdd > product.amount) {
  //   window.alert(
  //     "You can't add that amount. Available stock is " +
  //       (product.amount - currentInCart) +
  //       " unit(s)."
  //   );
  //   return false;
  // } else {
    if (
      myform.Product.value == "" ||
      myform.amount.value == "" ||
      myform.unitprice == "" ||
      myform.amount.value < 0 ||
      myform.total.value < 0 ||
      myform.totalTax.value < 0 ||
      myform.unitprice.value < 0
    ) {
      alert("Preencha todos os campos corretamente.");
    } else {
      var newProduct = {
        idLinha: code,
        idProd: myform.Product.value,
        prodName: productName,
        unitprice: myform.unitprice.value,
        amount: Amount,
        total: myform.total.value,
        justTax: myform.totalTax.value,
      };
      
      console.log(productVal);
      

      if(valItem(productVal)){
        alert("This product is already in your cart. To add it, delete what's in the cart and add a new one");
      }
      else{
        calcularTotal();
        // addCart(newProduct);
        fetchPost(myform.Product.value, Amount, myform.unitprice.value, myform.totalTax.value)
        myform.reset();
        
      }
    }
  }
);

//valida carrinho
async function valItem($idToConsult) {

  try{
    const data = await fetch(`http://localhost/cart?idToConsult=${$idToConsult}`);
    const records = await data.json();

    return records
  }
 catch (error) {
  console.error(error);
}}

//Manda pro DB
async function fetchPost(code, amount, uprice, tax) {
  try {
    const res = await fetch("http://localhost/cart", {
      method: "POST",
      body: JSON.stringify({
        product_code: code,
        price: uprice,
        amount: amount,
        tax: tax,
      }),
    });
  } catch (error) {
    // console.error(error);
  }
};

// CANCELAR COMPRA
cancel.addEventListener("click", (event) => {
  event.preventDefault();
  if (
    window.confirm(
      "Ao clicar em 'Ok' todos os itens presente no carrinho serão deletados. Deseja prosseguir?"
    ) == true
  ) {
    fetchCancel()
    location.reload();
    setTimeout(() => {
      alert("Carrinho deletado com sucesso");
    }, 250);
  }
});




// ADICIONA PRODUTOS DO LOCALSTORAGE NA TABELA
function addCart(code, name, amount, uprice, tax12) {
  var linhaCart = document.createElement("tr");

  var btDelete = document.createElement("button");
  btDelete.textContent = "Delete";

  // DELETAR PRODUTO NA TABELA
  btDelete.addEventListener("click", function () {
    linhaCart.remove();
    fetchDelete(code);
    calcularTotal();
  });

  var cellId = document.createElement("td");
  var cellProdName = document.createElement("td");
  var cellUnitPrice = document.createElement("td");
  var cellAmount = document.createElement("td");
  var cellTotal = document.createElement("td");
  var cellDelete = document.createElement("td");

  cellId.textContent = code;
  cellProdName.textContent = name;
  cellUnitPrice.textContent = uprice;
  cellAmount.textContent = amount;
  cellTotal.textContent = (amount*uprice + parseFloat(tax12));

  cellDelete = btDelete;

  linhaCart.appendChild(cellId);
  linhaCart.appendChild(cellProdName);
  linhaCart.appendChild(cellUnitPrice);
  linhaCart.appendChild(cellAmount);
  linhaCart.appendChild(cellTotal);

  linhaCart.appendChild(cellDelete);

  tabela2.appendChild(linhaCart);
}

window.onload = function () {
 getData()
};

async function fetchDelete(ida) {
  try {
    console.log(ida);
    
    const res = await fetch("http://localhost/cart", {
      method: "POST",
      body: JSON.stringify({
        idToConsult: ida
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
async function fetchCancel() {
  try {
    
    const res = await fetch("http://localhost/cart", {
      method: "POST",
      body: JSON.stringify({
        ctz: "SIM"
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
async function fetchFinish() {
  try {
    const total = document.querySelector("#totaled").value;
    const tax = document.querySelector("#taxed").value;

    const dataAtual = new Date().toLocaleString();

    console.log(total);
    console.log(tax);
    
    const res = await fetch("http://localhost/cart", {
      method: "POST",
      body: JSON.stringify({
        price2: total,
        tax2: tax,
        dia: dataAtual
      })
    })
      if (!res.ok) {
        throw new Error("Could not Finish");
      }
  } catch (error) {
    console.error(error);
    console.log("não finalizou");
    
  }
  location.reload()
}

// DELETA NO LOCALSTORAGE
function deleteProd(ida) {
  var idm = listaCart.map((e) => e.idLinha).indexOf(ida);
  listaCart.splice(idm, 1);
  localStorage.setItem("listaCart", JSON.stringify(listaCart));
}

// CALCULA TOTAL E TAXA
async function calcularTotal() {

  const unitprice = parseFloat(document.querySelector("#unitprice").value) || 0;
  const amount = parseInt(document.querySelector("#amount").value) || 0;
  const tax = parseFloat(document.querySelector("#tax").value) || 0;

  try {
    const records = await getCalculaTotalTax();
      document.querySelector("#taxed").value = records[0].tax;
      document.querySelector("#totaled").value = records[0].total;
    
  } catch (error) {
    console.error(error);
  }

  const total = amount * unitprice * (1 + tax / 100);
  const justTax = amount * unitprice * (tax / 100);

  document.querySelector("#total").value = total.toFixed(2);
  document.querySelector("#totalTax").value = justTax.toFixed(2);
}

//NÃO TOQUE NÃO ADICIONE () NA FUNÇÃO DA LINHA DE BAIXO ASSIM TA FUNCIONANDO E SE TIRAR NÃO FUNCIONA
document.querySelector("#amount").oninput = calcularTotal;
calcularTotal();



// ATUALIZA VALORES AO SELECIONAR PRODUTO

async function getInputTax() {

  try{
    const data = await fetch(`http://localhost/cart?ID_TO_CONSULT=${productSelect.value}`);
    const records = await data.json();

    return records
  }
 catch (error) {
  console.error(error);
}
}
async function getCalculaTotalTax() {

  try{
    const data = await fetch("http://localhost/cart?calcular=OK");
    const records = await data.json();
       
      return records
  }
 catch (error) {
  console.error(error);
}
}

productSelect.onchange = async () => {
  try {
    const records = await getInputTax();


    document.querySelector("#tax").value = records.tax;
    document.querySelector("#unitprice").value = records.price;
    
    calcularTotal();
  } catch (error) {
    console.error(error);
  }
};

// FINALIZAR COMPRA E ATUALIZAR ESTOQUE
finish.addEventListener("click", (event) => {
  event.preventDefault();

  // if (listaCart.length == 0) {
  //   window.alert("Adicione ao menos 1 produto para finalizar a compra.");
  // } else {
  //   listaCart.forEach((cartItem) => {
  //     const product = listaProd.find((p) => p.idProd == cartItem.idProd);
  //     if (product) {
  //       product.amount -= cartItem.amount;
  //     }
  //   });

    //ALTERA O AMOUNT NO PRODUCTS
    localStorage.setItem("listaProd", JSON.stringify(listaProd));

    // ADD CART NO HIST
    var savedCart = listaCart;
    var UltiCode = listaHist.at(-1)?.idCompra ?? 0;
    var code = UltiCode + 1;

    const dataAtual = new Date().toLocaleString();
    var pagamento = document.querySelector("#pagamento");

    var newSell = {
      idCompra: code,
      data: dataAtual,
      totalCompra: pagamento.totaled.value,
      totalTax: pagamento.taxed.value,
      cart: savedCart,
    };

    listaHist.push(newSell);
    localStorage.setItem("listaHist", JSON.stringify(listaHist));

    fetchFinish();

    // Limpa o carrinho
    listaCart = [];
    localStorage.setItem("listaCart", JSON.stringify([]));
    // location.reload();
  }
);
