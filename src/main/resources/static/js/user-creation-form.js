async function checkUsernameAvailability(event){
	
	event.preventDefault();
	
	const form = document.getElementById('user-creation-form');
	
	const formData = new FormData(form);
	
	const username = formData.get('username');
	
	const csrf = formData.get('_csrf');
	
	
	const res = await fetch("/api/user?username=" + username, {
		method : 'GET' ,
		headers : {
				'Content-type' : 'application/json',
				'X-CSRF-TOKEN' : csrf
			}
		}).catch(err=>console.error(err));
		
	const data = await res.json();
	
	alert(data.msg);
}


const checkAvailableUsernameBtn = document.querySelector('.check-available-username');

checkAvailableUsernameBtn.addEventListener('click', checkUsernameAvailability);
