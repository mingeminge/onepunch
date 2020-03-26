export function setDisabled(data, row) {
  data.forEach(d => {
    if (d.id === row.id) {
      d.disabled = true;
    }
    if (d.children) {
      d.children = setDisabled(d.children, row);
    }
  });
  return data;
}
