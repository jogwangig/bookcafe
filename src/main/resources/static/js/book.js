async function deleteBook(id){
	if(!confirm("정말 삭제하시겠습니까?")) return;
	
	const token = document.querySelector("meta[name='_csrf']").content;
	const header = document.querySelector("meta[name='_csrf_header']").content;
	
	const option = {
		
		method : 'DELETE',
		
		headers: {
		        [header]: token
		    }
	}
	
	const res = await fetch('/book/delete?bookId=' + id, option)
							.catch(err=>console.error(err));	
	
	if(res.ok){
		const data = await res.json();
		alert(data.msg);
		location.href ="/library";
	}else{
		alert("삭제 실패");
	}
		
			
	
}


const deleteBtns = document.querySelectorAll(".book-delete-btn")

deleteBtns.forEach(b=>{
	b.addEventListener('click' , (event)=>{
		const id = event.target.dataset.id;
		
		deleteBook(id);
	});
});


