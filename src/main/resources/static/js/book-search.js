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
	//document.querySelector('#bookshelf-select-container').style.display = "";
	document.querySelector('.black-box').style.display = 'none';
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
					const coverId = document.createElement('p');
					
					title.classList.add('title');
					author.classList.add('author');
					coverId.classList.add('cover-id');
					
					con.classList.add('book-container');
					con.addEventListener('click', addBook);
					
					img.src = coverImgUrl + c.cover_i + imgSize;
					title.innerHTML = c.title;
					author.innerHTML = c.author_name.slice(0 ,3);
					coverId.innerHTML = c.cover_i;
					coverId.style.display = 'none';
					
					con.appendChild(img);
					con.appendChild(title);
					con.appendChild(author);
					con.appendChild(coverId);
					
					container.appendChild(con);
				
			});
}


function filterCoverId(docs){
	const result = docs.filter(c => 'cover_i' in c);

	return result;
}

let imgSrc;
let titleValue;
let authorValue;


function addBook(event){
	const a = event.currentTarget;
	const img = a.querySelector('.cover-id');
	const b = a.querySelector('.title');
	const c = a.querySelector('.author');
	
	document.querySelector('#bookshelf-select-container').style.display = 'block';

	imgSrc = img.innerHTML;
	titleValue = b.innerHTML;
	authorValue = c.innerHTML;
	
	console.log(imgSrc + "  " + titleValue  +  "   "  +  authorValue);
}

async function fetchToServer(){
	const selectValue = document.querySelector('#bookshelf-select');
	
	//console.log(imgSrc + "  " + titleValue  +  "   "  +  authorValue  +  "      "  + selectValue.value);
	let query = "coverId=" + imgSrc  + "&title=" + titleValue  +  "&author=" + authorValue + "&bookShelfId=" + selectValue.value;
	console.log(query);
	document.querySelector('#bookshelf-select-container').style.display = 'none';
	
	await fetch("/api/book/create?" + query)
		.then(res=>res.text())
		.then(data=>console.log(data))
		.catch(err=>console.error(err));
}





const btn = document.getElementById('search-btn');
const fetchBtn = document.getElementById('register-btn');
btn.addEventListener('click', searchBook);
fetchBtn.addEventListener('click', fetchToServer);



