const searchUrl = "https://openlibrary.org/search.json?";

const coverImgUrl = "https://covers.openlibrary.org/b/id/";

const imgSize = "-M.jpg";

const options = {
    method: 'GET'
};

						
									
async function searchBook(){
	const title = document.getElementById('title').value;
	const author = document.getElementById('author').value;
	const isbn = document.getElementById('isbn').value;
	
	const createdUrl = createUrl(title, author, isbn);
	
	document.querySelector('.search-container').style.display = 'none';

	document.querySelector('.black-box').style.display = 'none';
	
	const res = await fetch(createdUrl, options);
	
	const searchResults = await res.json();
	
	displayWithCoverImg(searchResults.docs);
		
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
	
	docs.filter(doc => 'cover_i' in doc)
	.forEach((doc)=>{
		container.appendChild(createBookTag(doc));			
	});
}


let imgSrc;
let titleValue;
let authorValue;


function addBook(event){
	const a = event.currentTarget;
	const coverId = a.querySelector('.cover-id');
	const b = a.querySelector('.title');
	const c = a.querySelector('.author');
	
	document.querySelector('.black-box').style.display = 'block';
	document.querySelector('#bookshelf-select-container').style.display = 'block';

	imgSrc = coverId.innerHTML;
	titleValue = b.innerHTML;
	authorValue = c.innerHTML;
	
	console.log(imgSrc + "  " + titleValue  +  "   "  +  authorValue);
}

async function fetchToServer(){
	const selectValue = document.querySelector('#bookshelf-select');
	
	let query = "coverId=" + imgSrc  + "&title=" + titleValue  +  "&author=" + authorValue + "&bookShelfId=" + selectValue.value;

	document.querySelector('#bookshelf-select-container').style.display = 'none';
	
	const res = await fetch("/api/book/create?" + query);
	
	const data = await res.json();
		
	alert(data.msg)
		
	document.querySelector('.black-box').style.display = 'none';
}


function createBookTag(doc){		
		const bookContainer = document.createElement('div');
		const img = document.createElement('img');
		const title = document.createElement('p');
		const author = document.createElement('p');
		const isbn = document.createElement('p');
		const coverId = document.createElement('p');
		
		title.classList.add('title');
		author.classList.add('author');
		coverId.classList.add('cover-id');
		
		bookContainer.classList.add('book-container');
		bookContainer.addEventListener('click', addBook);
		
		img.src = coverImgUrl + doc.cover_i + imgSize;
		title.innerHTML = doc.title;
		author.innerHTML = doc.author_name.slice(0 ,3);
		coverId.innerHTML = doc.cover_i;
		coverId.style.display = 'none';
		
		bookContainer.appendChild(img);
		bookContainer.appendChild(title);
		bookContainer.appendChild(author);
		bookContainer.appendChild(coverId);
		
		return bookContainer;
}


function cancel(){
	document.querySelector('.black-box').style.display = 'none';
	document.querySelector('#bookshelf-select-container').style.display = 'none';
}



const btn = document.getElementById('search-btn');
const fetchBtn = document.getElementById('register-btn');
const cancelBtn = document.getElementById('cancel-btn');

btn.addEventListener('click', searchBook);
fetchBtn.addEventListener('click', fetchToServer);
cancelBtn.addEventListener('click', cancel);



