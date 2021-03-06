export const shortUrlTemplate = response =>
  `<div class="card">
    <div class="card-body d-flex justify-content-between">
      <div class="div-short-url">
        <span id="short-url-text">
          ${response.shortUrl}
        </span>
      </div>
      <div>
        <button class="btn btn-success short-url-copy-btn">Copy</button>
      </div>
    </div>
  </div>`;