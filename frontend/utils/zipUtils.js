// src/utils/zipUtils.js
/**
 * Download a blob response as a file (zip typically)
 * - response: axios response with { data: Blob, headers... }
 * - filenameFallback: used if Content-Disposition not present
 */
export const downloadBlobAsFile = (response, filenameFallback = 'reports.zip') => {
    if (!response || !response.data) {
      throw new Error('Invalid response to download');
    }
  
    const blob = new Blob([response.data], { type: response.data.type || 'application/zip' });
  
    // Try to get filename from Content-Disposition header
    const cd = response.headers && (response.headers['content-disposition'] || response.headers['Content-Disposition']);
    let filename = filenameFallback;
    if (cd) {
      const match = /filename\*?=(?:UTF-8'')?["']?([^;"']+)/i.exec(cd);
      if (match && match[1]) {
        filename = decodeURIComponent(match[1]);
      }
    }
  
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = filename;
    document.body.appendChild(a);
    a.click();
    a.remove();
    window.URL.revokeObjectURL(url);
  };
  
  /**
   * Helper: call reports/download endpoint (axios instance must be configured)
   * Example usage in service:
   * const res = await axiosInstance.get('/api/reports/download?from=...&to=...', { responseType: 'blob' });
   * downloadBlobAsFile(res, 'myreports.zip');
   */
  