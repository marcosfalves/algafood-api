const elements = document.querySelectorAll('.js-btn-on-click-revoke');
const form = document.getElementById('revokeForm');
const input = document.getElementById('clientIdInput');
elements.forEach(element => {
   element.addEventListener('click', (e)=>{
        input.value = element.dataset.clientid;
        form.submit();
   });
});