SELECT COUNT(*) FROM _security;

SELECT * FROM _security WHERE evtoebitda < 7 AND evtoebitda > 0  AND ebitda > 0  AND priceBook < 3 AND operatingCashFlow > 0 AND returnOnEquity > .1 AND percentHeldByInstitutions > .1 AND profitMargin > .05 AND returnOnAssets > .05 AND totalDebtToEquity < 50 ORDER BY evtoebitda;