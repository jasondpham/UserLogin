const username = document.getElementById('name')
const password = document.getElementById('password')
const form = document.getElementById('form')

$('#button').click(
    function () {
        alert("function")
    }
)
form.addEventListener('submit', (e) => {
    alert('hello');
    
})