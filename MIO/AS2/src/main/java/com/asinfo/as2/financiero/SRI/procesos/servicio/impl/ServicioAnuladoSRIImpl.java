/*   1:    */ package com.asinfo.as2.financiero.SRI.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.sri.AnuladoSRIDao;
/*   4:    */ import com.asinfo.as2.entities.FacturaCliente;
/*   5:    */ import com.asinfo.as2.entities.sri.AnuladoSRI;
/*   6:    */ import com.asinfo.as2.entities.sri.FacturaClienteSRI;
/*   7:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioAnuladoSRI;
/*   8:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   9:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  10:    */ import java.util.HashMap;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.ejb.EJB;
/*  14:    */ import javax.ejb.Stateless;
/*  15:    */ 
/*  16:    */ @Stateless
/*  17:    */ public class ServicioAnuladoSRIImpl
/*  18:    */   implements ServicioAnuladoSRI
/*  19:    */ {
/*  20:    */   @EJB
/*  21:    */   private AnuladoSRIDao anuladoSRIDao;
/*  22:    */   
/*  23:    */   public void guardar(AnuladoSRI anuladoSRI)
/*  24:    */     throws ExcepcionAS2Financiero
/*  25:    */   {
/*  26: 48 */     if (Integer.parseInt(anuladoSRI.getNumeroDesde()) > Integer.parseInt(anuladoSRI.getNumeroHasta())) {
/*  27: 49 */       throw new ExcepcionAS2Financiero("msg_error_numero_desde_hasta");
/*  28:    */     }
/*  29: 51 */     int desde = Integer.parseInt(anuladoSRI.getNumeroDesde());
/*  30: 52 */     int hasta = Integer.parseInt(anuladoSRI.getNumeroHasta());
/*  31: 53 */     int totalAnulados = hasta - desde + 1;
/*  32: 54 */     anuladoSRI.setNumeroRegistrosAnulados(Integer.valueOf(totalAnulados));
/*  33:    */     
/*  34:    */ 
/*  35: 57 */     this.anuladoSRIDao.guardar(anuladoSRI);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void eliminar(AnuladoSRI anuladoSRI)
/*  39:    */   {
/*  40: 67 */     this.anuladoSRIDao.eliminar(anuladoSRI);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public AnuladoSRI buscarPorId(int idAnuladoSRI)
/*  44:    */   {
/*  45: 77 */     return (AnuladoSRI)this.anuladoSRIDao.buscarPorId(Integer.valueOf(idAnuladoSRI));
/*  46:    */   }
/*  47:    */   
/*  48:    */   public List<AnuladoSRI> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean ordenar, Map<String, String> filters)
/*  49:    */   {
/*  50: 89 */     return this.anuladoSRIDao.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public List<AnuladoSRI> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  54:    */   {
/*  55: 94 */     if (filters == null) {
/*  56: 95 */       filters = new HashMap();
/*  57:    */     }
/*  58: 97 */     return this.anuladoSRIDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public int contarPorCriterio(Map<String, String> filters)
/*  62:    */   {
/*  63:107 */     return this.anuladoSRIDao.contarPorCriterio(filters);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void anularFacturaCliente(FacturaClienteSRI facturaClienteSRI)
/*  67:    */   {
/*  68:118 */     AnuladoSRI anuladoSRI = new AnuladoSRI();
/*  69:119 */     anuladoSRI.setIdOrganizacion(facturaClienteSRI.getIdOrganizacion());
/*  70:120 */     anuladoSRI.setIdSucursal(facturaClienteSRI.getIdSucursal());
/*  71:121 */     anuladoSRI.setTipoComprobanteSRI(facturaClienteSRI.getTipoComprobanteSRI());
/*  72:122 */     anuladoSRI.setAnio(FuncionesUtiles.getAnio(facturaClienteSRI.getFacturaCliente().getFecha()));
/*  73:123 */     anuladoSRI.setMes(FuncionesUtiles.getMes(facturaClienteSRI.getFacturaCliente().getFecha()));
/*  74:124 */     anuladoSRI.setEstablecimiento(facturaClienteSRI.getEstablecimiento());
/*  75:125 */     anuladoSRI.setPuntoEmision(facturaClienteSRI.getPuntoEmision());
/*  76:126 */     anuladoSRI.setNumeroDesde(facturaClienteSRI.getNumero());
/*  77:127 */     anuladoSRI.setNumeroHasta(facturaClienteSRI.getNumero());
/*  78:128 */     anuladoSRI.setAutorizacion(facturaClienteSRI.getAutorizacion());
/*  79:129 */     anuladoSRI.setNumeroRegistrosAnulados(Integer.valueOf(1));
/*  80:130 */     this.anuladoSRIDao.guardar(anuladoSRI);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public List<AnuladoSRI> obtenerAnuladosMes(int anio, int mes, int idOrganizacion)
/*  84:    */   {
/*  85:140 */     return this.anuladoSRIDao.obtenerAnuladosMes(anio, mes, idOrganizacion);
/*  86:    */   }
/*  87:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.SRI.procesos.servicio.impl.ServicioAnuladoSRIImpl
 * JD-Core Version:    0.7.0.1
 */