async function modifyComment(id, authorType){
		
	const token = document.querySelector("meta[name='_csrf']").content;
	const header = document.querySelector("meta[name='_csrf_header']").content;
	
	const commentForm = document.querySelector('.comment-form');
	
	
	const option = {
		
		method : 'GET',
		
		headers: {
		        [header]: token
		    }
	}
			
			console.log(authorType);
			
			
			if(authorType == 'user'){
				const res = await fetch('/api/post/comment/modify?commentId=' + id, option);
				

				const data = await res.json();
				
				console.log(commentForm.elements['content'].value);
				
				if(res.ok){
					commentForm.elements['content'].value = data.content;
					console.log(data.content);
				}else{
					alert(data);
				}
				
				
			}else{
				
				const postPwd = prompt("비밀번호를 입력해 주세요", "");
							
				const anonymousOption = {
						
						method : 'POST',
						
						headers: {
						        [header]: token,
								'Content-Type': 'application/json' 
						    },
						body : JSON.stringify({
								pwd : postPwd
							})
						
					};
					
					
				
				const res= await fetch('/api/post/delete?postId=' + id, anonymousOption);
				const data = await res.text();
				
				if(res.ok){
					alert(data);
					location.href = '/board?boardId='+ boardId;
				}else{
					alert(data);
				}
						
			}
			
	
}

const commentForm = document.querySelector(".comment-form");

async function createComment(event){
		
	event.preventDefault();
	
	const postId = commentForm.dataset.postId;
	
	const formData = new FormData(commentForm);
	
	
	const csrf = formData.get('_csrf');
	
	const data = {
		'content' : formData.get('content')
	};
		
	const option = {
		method : 'POST',
		headers : {
			'Content-type' : 'application/json',
			'X-CSRF-TOKEN' : csrf
		},
		body : JSON.stringify(data)
	};
	
	const res = await fetch('/api/post/comment?type=create&postId='+postId, option);
	
	if(res.ok)
		alert("성공");
	
	location.reload();
}









commentForm.addEventListener('submit', createComment);

const modifyBtns = document.querySelectorAll(".comment-modify");

modifyBtns.forEach(b=>{
	b.addEventListener('click' , (event)=>{
		const id = event.target.dataset.id;
		const authorType = event.target.dataset.authorType;
		
		modifyComment(id , authorType);
	});
});




