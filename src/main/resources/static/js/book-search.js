const searchUrl = "https://openlibrary.org/search.json?";


const coverImgUrl = "https://covers.openlibrary.org/b/id/";

const createdUrl = createUrl("how the mind works", "", "");
//console.log(createdUrl);

const options = {
    method: 'GET'
};
/*

fetch(createdUrl, options)
    .then(response => response.json())
    .then(data => console.log(data))
    .catch(error => console.error('Error:', error));
	*/
/*
 fetch(createdUrl, options)
					  .then(response => response.json())
					    .then(data => console.log(filterCoverId(data.docs)))
					    .catch(error => console.error('Error:', error));*/
						
async function searchBook(){
	const title = document.getElementById('title').value;
	const author = document.getElementById('author').value;
	const isbn = document.getElementById('isbn').value;
	
	const createdUrl = createUrl(title, author, isbn);
	
	document.querySelector('.search-container').style.display = 'none';
	
	await fetch(createdUrl, options)
				.then(response => response.json())
				.then(data=>{
					console.log(data);
					displayWithCoverImg(data.docs);})
				.catch(error => console.error('Error:', error));
	
	
	console.log(createdUrl);
	
}

	


function createUrl(title, author_name, isbn){
	
	const conditions = ['title', 'author', 'isbn'];
	
	const conditionValues = [title, author_name, isbn].map(v=>v.replaceAll(" ","+"));
	
	let result = searchUrl;
	
	let conditionResults = "";
	
	for(let i = 0; i <conditions.length; i++){
		if(conditionValues[i] == "") continue;
				
		if(conditionResults != "") conditionResults += "&";
		
		conditionResults += conditions[i] + "=" + conditionValues[i];

	}
	
	return result + conditionResults + "&fields=title,author_name,isbn,cover_i";
	
}


function displayWithCoverImg(docs){
	const container = document.querySelector('.content-fragment');
	const imgSize = "-M.jpg";
	
			docs.filter(c => 'cover_i' in c)
				.forEach((c)=>{
					const con = document.createElement('div');
					const img = document.createElement('img');
					const title = document.createElement('p');
					const author = document.createElement('p');
					const isbn = document.createElement('p');
					
					img.src = coverImgUrl + c.cover_i + imgSize;
					title.innerHTML = "제목 : " +  c.title;
					author.innerHTML = "저자 : " + c.author_name.slice(0 ,3);
					
					con.appendChild(img);
					con.appendChild(title);
					con.appendChild(author);
					
					container.appendChild(con);
				
			});
}


function filterCoverId(docs){
	const result = docs.filter(c => 'cover_i' in c);

	return result;
}


const btn = document.getElementById('search-btn');
btn.addEventListener('click', searchBook);



