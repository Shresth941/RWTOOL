// src/utils/apiResponse.js
/**
 * Small client-side helper to normalize API responses.
 * Backend controllers often return either:
 *  - { data: ..., message: ..., success: true }
 *  - raw payload
 *
 * Use wrapSuccess / wrapError to prepare UI payloads or to standardize emitted events.
 */

export const wrapSuccess = (payload, message = 'OK') => ({
    ok: true,
    message,
    data: payload,
  });
  
  export const wrapError = (errorMessage = 'Something went wrong', status = 500, details = null) => ({
    ok: false,
    message: errorMessage,
    status,
    details,
  });
  
  export const isOk = (res) => res && (res.ok === true || (res.status >= 200 && res.status < 300));
  
  /**
   * Extracts a useful message from an axios error object
   */
  export const extractErrorMessage = (err) => {
    if (!err) return 'Unknown error';
    if (err.response && err.response.data) {
      const data = err.response.data;
      // try common shapes
      if (typeof data === 'string') return data;
      if (data.message) return data.message;
      if (data.error) return data.error;
      // else stringify small snippet
      return JSON.stringify(data).slice(0, 500);
    }
    if (err.message) return err.message;
    return String(err);
  };
  