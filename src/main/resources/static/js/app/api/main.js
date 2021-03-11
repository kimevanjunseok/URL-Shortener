const METHOD = {
  POST(data) {
    return {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        ...data
      })
    };
  }
};

const api = (() => {
  const request = (uri, config) => fetch(uri, config).then(data => data.json());

  const shortUrl = {
    create(data) {
      return request(`/api/v1/short-url`, METHOD.POST(data));
    }
  };

  return {
    shortUrl
  };
})();

export default api;

