/*   1:    */ package com.asinfo.as2.mantenimiento.configuracion.impl.old;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.mantenimiento.old.DestinoCostoDao;
/*   4:    */ import com.asinfo.as2.entities.DestinoCosto;
/*   5:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   6:    */ import com.asinfo.as2.mantenimiento.configuracion.old.ServicioDestinoCosto;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import javax.ejb.EJB;
/*  11:    */ import javax.ejb.Stateless;
/*  12:    */ 
/*  13:    */ @Stateless
/*  14:    */ public class ServicioDestinoCostoImpl
/*  15:    */   implements ServicioDestinoCosto
/*  16:    */ {
/*  17:    */   @EJB
/*  18:    */   private DestinoCostoDao destinoCostoDao;
/*  19:    */   
/*  20:    */   public void guardar(DestinoCosto destinoCosto)
/*  21:    */   {
/*  22: 47 */     this.destinoCostoDao.guardar(destinoCosto);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void eliminar(DestinoCosto destinoCosto)
/*  26:    */   {
/*  27: 59 */     this.destinoCostoDao.eliminar(destinoCosto);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public DestinoCosto buscarPorId(int idDestinoCosto)
/*  31:    */   {
/*  32: 70 */     return (DestinoCosto)this.destinoCostoDao.buscarPorId(Integer.valueOf(idDestinoCosto));
/*  33:    */   }
/*  34:    */   
/*  35:    */   public List<DestinoCosto> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  36:    */   {
/*  37: 82 */     return this.destinoCostoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public List<DestinoCosto> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  41:    */   {
/*  42: 94 */     return this.destinoCostoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public int contarPorCriterio(Map<String, String> filters)
/*  46:    */   {
/*  47:106 */     return this.destinoCostoDao.contarPorCriterio(filters);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public List<DestinoCosto> autocompletarDestinoCosto(String nombre)
/*  51:    */   {
/*  52:117 */     HashMap<String, String> filters = new HashMap();
/*  53:118 */     filters.put("activo", "true");
/*  54:119 */     filters.put("OR~nombre", nombre);
/*  55:120 */     filters.put("OR~codigo", nombre);
/*  56:121 */     return this.destinoCostoDao.obtenerListaCombo("nombre", true, filters);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public DestinoCosto buscarPorCodigo(String codigo)
/*  60:    */     throws ExcepcionAS2
/*  61:    */   {
/*  62:132 */     return this.destinoCostoDao.buscarPorCodigo(codigo);
/*  63:    */   }
/*  64:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.impl.old.ServicioDestinoCostoImpl
 * JD-Core Version:    0.7.0.1
 */