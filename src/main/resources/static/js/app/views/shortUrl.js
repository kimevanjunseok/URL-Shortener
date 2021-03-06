import api from "../api/main.js";
import {shortUrlTemplate} from "../utils/templates.js";
import {EVENT_TYPE} from "../utils/constants.js";

function ShortUrl() {
  const $originUrlInput = document.querySelector("#origin-url-input");
  const $shortUrl = document.querySelector("#short-url");
  const $shortenBtn = document.querySelector("#shorten-btn");

  const createShortUrl = () => {
    const request = {
      url: $originUrlInput.value
    };
    api.shortUrl.create(request).then(data => {
      $shortUrl.innerHTML = shortUrlTemplate(data);
    })
  };

  const copyShortUrl = event => {
    const $target = event.target;
    const shortUrlCopyBtn = $target.classList.contains("short-url-copy-btn")
    if (shortUrlCopyBtn) {
      const $shortUrlText = document.querySelector("#short-url-text");
      const range = document.createRange();
      range.selectNode($shortUrlText);
      window.getSelection().removeAllRanges();
      window.getSelection().addRange(range);
      document.execCommand("copy");
      window.getSelection().removeAllRanges();

      const $shortUrlCopyBtn = document.querySelector(".short-url-copy-btn");
      $shortUrlCopyBtn.innerHTML = "Copied!"
    }
  };

  const initEventListeners = () => {
    $shortenBtn.addEventListener(EVENT_TYPE.CLICK, createShortUrl);
    $shortUrl.addEventListener(EVENT_TYPE.CLICK, copyShortUrl);
  };

  const init = () => {
    initEventListeners();
  };

  return {
    init
  };
}

const shortUrl = new ShortUrl();
shortUrl.init();