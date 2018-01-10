/*   1:    */ package com.asinfo.as2.inventario.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.ConjuntoAtributoDao;
/*   4:    */ import com.asinfo.as2.entities.Atributo;
/*   5:    */ import com.asinfo.as2.entities.ConjuntoAtributo;
/*   6:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioConjuntoAtributo;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import javax.ejb.EJB;
/*  11:    */ import javax.ejb.Stateless;
/*  12:    */ 
/*  13:    */ @Stateless
/*  14:    */ public class ServicioConjuntoAtributoImpl
/*  15:    */   implements ServicioConjuntoAtributo
/*  16:    */ {
/*  17:    */   @EJB
/*  18:    */   private ConjuntoAtributoDao conjuntoAtributoDao;
/*  19:    */   
/*  20:    */   public void guardar(ConjuntoAtributo conjuntoAtributo)
/*  21:    */   {
/*  22: 43 */     if (conjuntoAtributo.getId() == 0)
/*  23:    */     {
/*  24: 44 */       List<Atributo> lista = conjuntoAtributo.getListaAtributo();
/*  25: 45 */       conjuntoAtributo.setListaAtributo(null);
/*  26: 46 */       this.conjuntoAtributoDao.guardar(conjuntoAtributo);
/*  27:    */       
/*  28: 48 */       conjuntoAtributo.setListaAtributo(lista);
/*  29: 49 */       this.conjuntoAtributoDao.guardar(conjuntoAtributo);
/*  30:    */     }
/*  31:    */     else
/*  32:    */     {
/*  33: 51 */       this.conjuntoAtributoDao.guardar(conjuntoAtributo);
/*  34:    */     }
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void eliminar(ConjuntoAtributo conjuntoAtributo)
/*  38:    */   {
/*  39: 62 */     this.conjuntoAtributoDao.eliminar(conjuntoAtributo);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public ConjuntoAtributo buscarPorId(int id)
/*  43:    */   {
/*  44: 72 */     return (ConjuntoAtributo)this.conjuntoAtributoDao.buscarPorId(Integer.valueOf(id));
/*  45:    */   }
/*  46:    */   
/*  47:    */   public ConjuntoAtributo cargarDetalle(int id)
/*  48:    */   {
/*  49: 83 */     return this.conjuntoAtributoDao.cargarDetalle(id);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public List<ConjuntoAtributo> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  53:    */   {
/*  54: 94 */     return this.conjuntoAtributoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public int contarPorCriterio(Map<String, String> filters)
/*  58:    */   {
/*  59:104 */     return this.conjuntoAtributoDao.contarPorCriterio(filters);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public List<ConjuntoAtributo> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  63:    */   {
/*  64:114 */     return this.conjuntoAtributoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public List<ConjuntoAtributo> obtenerListaComboPorIndicador(int idOrganizacion, boolean indicadorProducto, boolean indicadorCliente, boolean indicadorProveedor)
/*  68:    */   {
/*  69:120 */     List<ConjuntoAtributo> lresult = new ArrayList();
/*  70:121 */     if (indicadorProducto) {
/*  71:122 */       lresult.addAll(this.conjuntoAtributoDao.obtenerListaComboIndicadorProducto(idOrganizacion));
/*  72:    */     }
/*  73:124 */     if (indicadorCliente) {
/*  74:125 */       lresult.addAll(this.conjuntoAtributoDao.obtenerListaComboIndicadorCliente(idOrganizacion));
/*  75:    */     }
/*  76:127 */     if (indicadorProveedor) {
/*  77:128 */       lresult.addAll(this.conjuntoAtributoDao.obtenerListaComboIndicadorProveedor(idOrganizacion));
/*  78:    */     }
/*  79:131 */     return lresult;
/*  80:    */   }
/*  81:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioConjuntoAtributoImpl
 * JD-Core Version:    0.7.0.1
 */