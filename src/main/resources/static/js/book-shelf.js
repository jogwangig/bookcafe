async function deleteBookShelf(id){
	if(!confirm("정말 삭제하시겠습니까?")) return;
	
	const token = document.querySelector("meta[name='_csrf']").content;
	const header = document.querySelector("meta[name='_csrf_header']").content;
	
	const option = {
		
		method : 'DELETE',
		
		headers: {
		        [header]: token
		    }
	}
	
	await fetch('/book-shelf/delete?bookShelfId=' + id, option)
			.then(res=>(res.ok)?res.text():alert("삭제 실패"))
			.then(t=>{alert(t);
					location.href ="/library";})
			.catch(err=>console.error(err));	
	
}


const deleteBtns = document.querySelectorAll("button");

deleteBtns.forEach(b=>{
	b.addEventListener('click' , (event)=>{
		const id = event.target.dataset.id;
		
		deleteBookShelf(id);
	});
});


