document.addEventListener('DOMContentLoaded', () => {
  const phone = document.querySelector('input[type="tel"]');
  if (phone) {
    phone.addEventListener('input', () => {
      phone.value = phone.value.replace(/[^0-9+()\-\s]/g, '').slice(0, 30);
    });
  }
});
