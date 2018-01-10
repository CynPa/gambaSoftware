/*   1:    */ package com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.CuentaContableDao;
/*   4:    */ import com.asinfo.as2.dao.NivelCuentaDao;
/*   5:    */ import com.asinfo.as2.entities.CuentaContable;
/*   6:    */ import com.asinfo.as2.entities.NivelCuenta;
/*   7:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioNivelCuenta;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import javax.ejb.EJB;
/*  11:    */ import javax.ejb.Stateless;
/*  12:    */ 
/*  13:    */ @Stateless
/*  14:    */ public class ServicioNivelCuentaImpl
/*  15:    */   implements ServicioNivelCuenta
/*  16:    */ {
/*  17:    */   @EJB
/*  18:    */   private NivelCuentaDao nivelCuentaDao;
/*  19:    */   @EJB
/*  20:    */   private CuentaContableDao cuentaContableDao;
/*  21:    */   
/*  22:    */   public void guardar(NivelCuenta nivelCuenta)
/*  23:    */   {
/*  24: 44 */     this.nivelCuentaDao.guardar(nivelCuenta);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void eliminar(NivelCuenta nivelCuenta)
/*  28:    */   {
/*  29: 55 */     this.nivelCuentaDao.eliminar(nivelCuenta);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public NivelCuenta buscarPorId(Integer id)
/*  33:    */   {
/*  34: 65 */     return (NivelCuenta)this.nivelCuentaDao.buscarPorId(id);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public String getMascara(int idCuentaPadre, int idNivel)
/*  38:    */   {
/*  39: 75 */     int codigo = 0;
/*  40: 76 */     CuentaContable cuentaPadre = (CuentaContable)this.cuentaContableDao.buscarPorId(Integer.valueOf(idCuentaPadre));
/*  41: 77 */     if (cuentaPadre == null)
/*  42:    */     {
/*  43: 78 */       cuentaPadre = new CuentaContable();
/*  44: 79 */       cuentaPadre.setCodigo("");
/*  45:    */     }
/*  46:    */     else
/*  47:    */     {
/*  48: 81 */       codigo = cuentaPadre.getNivelCuenta().getCodigo();
/*  49:    */     }
/*  50: 83 */     NivelCuenta nivelCuenta = buscarPorId(Integer.valueOf(idNivel));
/*  51:    */     
/*  52: 85 */     List<NivelCuenta> niveles = obtenerListaCombo("codigo", true, null);
/*  53:    */     
/*  54: 87 */     String mascara = cuentaPadre.getCodigo();
/*  55: 89 */     for (NivelCuenta nivelCuentaX : niveles) {
/*  56: 90 */       if ((nivelCuentaX.getCodigo() > codigo) && 
/*  57: 91 */         (nivelCuentaX.getCodigo() <= nivelCuenta.getCodigo()))
/*  58:    */       {
/*  59: 92 */         for (int i = 0; i < nivelCuentaX.getLongitud(); i++) {
/*  60: 93 */           mascara = mascara + "9";
/*  61:    */         }
/*  62: 95 */         mascara = mascara + ".";
/*  63:    */       }
/*  64:    */     }
/*  65: 99 */     return mascara;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public List<NivelCuenta> obtenerTodosOrdenadoDescendente()
/*  69:    */   {
/*  70:109 */     return this.nivelCuentaDao.obtenerListaCombo("codigo", false, null);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public List<NivelCuenta> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  74:    */   {
/*  75:114 */     return this.nivelCuentaDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public List<NivelCuenta> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  79:    */   {
/*  80:124 */     return this.nivelCuentaDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public int ContarPorCriterio(Map<String, String> filters)
/*  84:    */   {
/*  85:134 */     return this.nivelCuentaDao.contarPorCriterio(filters);
/*  86:    */   }
/*  87:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl.ServicioNivelCuentaImpl
 * JD-Core Version:    0.7.0.1
 */