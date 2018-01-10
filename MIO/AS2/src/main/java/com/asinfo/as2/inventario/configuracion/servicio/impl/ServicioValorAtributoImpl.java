/*   1:    */ package com.asinfo.as2.inventario.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.ValorAtributoDao;
/*   4:    */ import com.asinfo.as2.entities.Atributo;
/*   5:    */ import com.asinfo.as2.entities.ValorAtributo;
/*   6:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioValorAtributo;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import javax.ejb.EJB;
/*  11:    */ import javax.ejb.Stateless;
/*  12:    */ 
/*  13:    */ @Stateless
/*  14:    */ public class ServicioValorAtributoImpl
/*  15:    */   implements ServicioValorAtributo
/*  16:    */ {
/*  17:    */   @EJB
/*  18:    */   private ValorAtributoDao valorAtributoDao;
/*  19:    */   
/*  20:    */   public void guardar(ValorAtributo valorAtributo)
/*  21:    */   {
/*  22: 45 */     this.valorAtributoDao.guardar(valorAtributo);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void eliminar(ValorAtributo valorAtributo)
/*  26:    */   {
/*  27: 56 */     this.valorAtributoDao.eliminar(valorAtributo);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public ValorAtributo buscarPorId(int id)
/*  31:    */   {
/*  32: 68 */     return (ValorAtributo)this.valorAtributoDao.buscarPorId(Integer.valueOf(id));
/*  33:    */   }
/*  34:    */   
/*  35:    */   public List<ValorAtributo> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  36:    */   {
/*  37: 74 */     return this.valorAtributoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public List<ValorAtributo> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  41:    */   {
/*  42: 86 */     return this.valorAtributoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public int contarPorCriterio(Map<String, String> filters)
/*  46:    */   {
/*  47: 98 */     return this.valorAtributoDao.contarPorCriterio(filters);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public List<ValorAtributo> autocompletarValorAtributo(String consulta, Atributo atributo)
/*  51:    */   {
/*  52:104 */     HashMap<String, String> filtros = new HashMap();
/*  53:105 */     filtros.put("atributo.idAtributo", Integer.toString(atributo.getIdAtributo()));
/*  54:106 */     filtros.put("codigo", consulta.trim());
/*  55:107 */     filtros.put("nombre", consulta.trim());
/*  56:108 */     filtros.put("indicadorProducto", "true");
/*  57:    */     
/*  58:    */ 
/*  59:111 */     return this.valorAtributoDao.obtenerListaCombo("nombre", true, filtros);
/*  60:    */   }
/*  61:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioValorAtributoImpl
 * JD-Core Version:    0.7.0.1
 */