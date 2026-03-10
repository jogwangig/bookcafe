const token = document.querySelector("meta[name='_csrf']").content;
const header = document.querySelector("meta[name='_csrf_header']").content;

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

async function sendEmailAuthCode(event){
	
	event.preventDefault();
	
	const emailAddress = document.querySelector('#emailAddress').value;
	
	const option = {
		method : 'GET',		
		headers: { [header]: token } 
	};
	
	console.log(emailAddress + "ddddd");
	
	const res = await fetch("/api/user/send/email/auth?emailAddress="+emailAddress, option);
	
	const data = await res.json();
	
	alert(data.msg);
}

async function verifyEmailAuthCode(event){
	event.preventDefault();
	
	const emailAddress = document.querySelector('#emailAddress').value;
	const emailAuthCode = document.querySelector('#emailAuthCode').value;
	
	const option = {
		method : 'POST',
		headers: { [header]: token, 'Content-Type': 'application/json'},
		body : JSON.stringify({
			'emailAddress' : emailAddress,
			'emailAuthCode'	: emailAuthCode	
		})};
		
		const res = await fetch("/api/user/verify/email/auth" , option);
		
		const data = await res.json();
			
		alert(data.msg);
}




const checkAvailableUsernameBtn = document.querySelector('.check-available-username');
const sendAuthCodeBtn = document.querySelector('.send-auth-code');
const emailAuthBtn = document.querySelector('.verify-auth-code');

checkAvailableUsernameBtn.addEventListener('click', checkUsernameAvailability);
sendAuthCodeBtn.addEventListener('click', sendEmailAuthCode);
emailAuthBtn.addEventListener('click', verifyEmailAuthCode);
