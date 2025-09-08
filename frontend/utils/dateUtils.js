// src/utils/dateUtils.js
/**
 * Small date utilities used by the UI.
 * Avoids adding a heavy library — uses native Date and Intl.DateTimeFormat.
 */

const DEFAULT_FORMAT = "yyyy-MM-dd";

/**
 * Format a Date (or ISO string) to yyyy-MM-dd (useful for API query params)
 */
export const toISODate = (dateLike) => {
  if (!dateLike) return null;
  const d = typeof dateLike === 'string' ? new Date(dateLike) : dateLike;
  if (Number.isNaN(d.getTime())) return null;
  const yyyy = d.getFullYear();
  const mm = String(d.getMonth() + 1).padStart(2, '0');
  const dd = String(d.getDate()).padStart(2, '0');
  return `${yyyy}-${mm}-${dd}`;
};

/**
 * Human-friendly formatting e.g. "13 Jun 2025" or according to locale
 */
export const formatHuman = (dateLike, locale = 'en-GB', options = {}) => {
  const d = typeof dateLike === 'string' ? new Date(dateLike) : dateLike;
  if (!d || Number.isNaN(d.getTime())) return '';
  const formatter = new Intl.DateTimeFormat(locale, {
    year: 'numeric',
    month: 'short',
    day: '2-digit',
    ...options,
  });
  return formatter.format(d);
};

/**
 * Given 'today', returns start/end ISO date strings for day/week/month
 */
export const rangeForFilter = (filter) => {
  const now = new Date();
  let from, to;
  to = now;
  switch (filter) {
    case 'today':
      from = new Date(now.getFullYear(), now.getMonth(), now.getDate());
      break;
    case 'week':
      // last 7 days
      from = new Date(new Date().setDate(now.getDate() - 7));
      break;
    case 'month':
      from = new Date(now.getFullYear(), now.getMonth(), 1);
      break;
    case 'year':
      from = new Date(now.getFullYear(), 0, 1);
      break;
    default:
      from = null;
  }
  return { from: from ? toISODate(from) : null, to: toISODate(to) };
};
