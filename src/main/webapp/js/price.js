var prices = document.getElementsByClassName('price');
var hidens = document.getElementsByClassName('pricehiden');
var counts = document.getElementsByClassName('count');
var allsum = document.getElementsByClassName('allsum')[0];
var sum = 0;
for (i = 0; prices.length > i; i++) {
    var count = counts[i].value.replace(',', '.');
    counts[i].value = count;
    if (hidens[i].value != 0) {
        prices[i].value = hidens[i].value * count;
    }
    sum += prices[i].value*1;

}
allsum.value = sum;

function changePrice() {
    var prices = document.getElementsByClassName('price');
    var hidens = document.getElementsByClassName('pricehiden');
    var counts = document.getElementsByClassName('count');
    var allsum = document.getElementsByClassName('allsum')[0];
    var sum = 0;
    for (i = 0; prices.length > i; i++) {
        var count = counts[i].value.replace(',', '.');
        counts[i].value = count;
        if (hidens[i].value != 0) {
            prices[i].value = hidens[i].value * count;
        }
        sum += prices[i].value*1;

    }
    allsum.value = sum;
}