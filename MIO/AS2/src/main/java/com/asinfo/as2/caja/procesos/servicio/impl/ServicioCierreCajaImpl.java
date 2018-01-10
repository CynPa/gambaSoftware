/*   1:    */ package com.asinfo.as2.caja.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.caja.procesos.servicio.ServicioCierreCaja;
/*   4:    */ import com.asinfo.as2.dao.CierreCajaDao;
/*   5:    */ import com.asinfo.as2.dao.DetalleCierreCajaDao;
/*   6:    */ import com.asinfo.as2.dao.DetalleFormaCobroDao;
/*   7:    */ import com.asinfo.as2.dao.GenericoDao;
/*   8:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   9:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*  10:    */ import com.asinfo.as2.entities.AnticipoCliente;
/*  11:    */ import com.asinfo.as2.entities.CierreCaja;
/*  12:    */ import com.asinfo.as2.entities.DetalleCierreCaja;
/*  13:    */ import com.asinfo.as2.entities.DetalleDenominacionFormaCobro;
/*  14:    */ import com.asinfo.as2.entities.DetalleFormaCobro;
/*  15:    */ import com.asinfo.as2.entities.Documento;
/*  16:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  17:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  18:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  19:    */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioAnticipoCliente;
/*  20:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  21:    */ import java.util.List;
/*  22:    */ import java.util.Map;
/*  23:    */ import javax.ejb.EJB;
/*  24:    */ import javax.ejb.Stateless;
/*  25:    */ 
/*  26:    */ @Stateless
/*  27:    */ public class ServicioCierreCajaImpl
/*  28:    */   implements ServicioCierreCaja
/*  29:    */ {
/*  30:    */   @EJB
/*  31:    */   private ServicioSecuencia servicioSecuencia;
/*  32:    */   @EJB
/*  33:    */   private ServicioDocumento servicioDocumento;
/*  34:    */   @EJB
/*  35:    */   private CierreCajaDao cierreCajaDao;
/*  36:    */   @EJB
/*  37:    */   private DetalleCierreCajaDao detalleCierreCajaDao;
/*  38:    */   @EJB
/*  39:    */   private DetalleFormaCobroDao detalleFormaCobroDao;
/*  40:    */   @EJB
/*  41:    */   private ServicioAnticipoCliente servicioAnticipoCliente;
/*  42:    */   @EJB
/*  43:    */   private transient GenericoDao<DetalleDenominacionFormaCobro> detalleDenominacionFormaCobroDao;
/*  44:    */   
/*  45:    */   public void guardar(CierreCaja cierreCaja)
/*  46:    */     throws ExcepcionAS2
/*  47:    */   {
/*  48: 71 */     validar(cierreCaja);
/*  49:    */     
/*  50: 73 */     cargarSecuencia(cierreCaja);
/*  51: 75 */     if (cierreCaja.getListaDetalleCierreCaja() != null) {
/*  52: 76 */       for (DetalleCierreCaja detalleCierreCaja : cierreCaja.getListaDetalleCierreCaja()) {
/*  53: 77 */         this.detalleCierreCajaDao.guardar(detalleCierreCaja);
/*  54:    */       }
/*  55:    */     }
/*  56: 81 */     this.cierreCajaDao.guardar(cierreCaja);
/*  57: 81 */     if (cierreCaja.getListDetalleDenominacionFormaCobro() != null) {
/*  58: 82 */       for (DetalleDenominacionFormaCobro ddfc : cierreCaja.getListDetalleDenominacionFormaCobro()) {
/*  59: 83 */         this.detalleDenominacionFormaCobroDao.guardar(ddfc);
/*  60:    */       }
/*  61:    */     }
/*  62:    */   }
/*  63:    */   
/*  64:    */   private void cargarSecuencia(CierreCaja cierreCaja)
/*  65:    */     throws ExcepcionAS2
/*  66:    */   {
/*  67: 89 */     List<Documento> listaDocumento = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.CIERRE_CAJA);
/*  68: 90 */     if (listaDocumento.size() > 0)
/*  69:    */     {
/*  70: 91 */       Documento documento = (Documento)listaDocumento.get(0);
/*  71: 92 */       if ((cierreCaja.getNumero() == null) || (cierreCaja.getNumero().isEmpty()))
/*  72:    */       {
/*  73: 93 */         String numero = this.servicioSecuencia.obtenerSecuenciaDocumento(documento.getIdDocumento(), cierreCaja.getFechaHasta());
/*  74: 94 */         cierreCaja.setNumero(numero);
/*  75:    */       }
/*  76:    */     }
/*  77:    */     else
/*  78:    */     {
/*  79: 97 */       throw new ExcepcionAS2("msg_secuencia_no_encontrada", DocumentoBase.CIERRE_CAJA.getNombre());
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void anular(CierreCaja cierreCaja)
/*  84:    */     throws ExcepcionAS2Financiero
/*  85:    */   {
/*  86:108 */     esEditable(cierreCaja);
/*  87:109 */     cierreCaja = cargarDetalle(cierreCaja.getIdCierreCaja());
/*  88:110 */     for (DetalleCierreCaja detalleCierreCaja : cierreCaja.getListaDetalleCierreCaja())
/*  89:    */     {
/*  90:111 */       detalleCierreCaja.setEliminado(true);
/*  91:112 */       this.detalleCierreCajaDao.guardar(detalleCierreCaja);
/*  92:    */     }
/*  93:114 */     cierreCaja.setEstado(Estado.ANULADO);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public CierreCaja buscarPorId(int idCierreCaja)
/*  97:    */   {
/*  98:124 */     return (CierreCaja)this.cierreCajaDao.buscarPorId(Integer.valueOf(idCierreCaja));
/*  99:    */   }
/* 100:    */   
/* 101:    */   public List<CierreCaja> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 102:    */   {
/* 103:134 */     return this.cierreCajaDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public List<CierreCaja> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 107:    */   {
/* 108:144 */     return this.cierreCajaDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public int contarPorCriterio(Map<String, String> filters)
/* 112:    */   {
/* 113:154 */     return this.cierreCajaDao.contarPorCriterio(filters);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public CierreCaja cargarDetalle(int idCierreCaja)
/* 117:    */   {
/* 118:164 */     return this.cierreCajaDao.cargarDetalle(idCierreCaja);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void esEditable(CierreCaja cierreCaja)
/* 122:    */     throws ExcepcionAS2Financiero
/* 123:    */   {
/* 124:174 */     cierreCaja = cargarDetalle(cierreCaja.getIdCierreCaja());
/* 125:175 */     if (cierreCaja.getEstado() == Estado.ANULADO) {
/* 126:176 */       throw new ExcepcionAS2Financiero("msg_error_anular_cierre_caja_anulado");
/* 127:    */     }
/* 128:178 */     for (DetalleCierreCaja detalleCierreCaja : cierreCaja.getListaDetalleCierreCaja()) {
/* 129:179 */       if (detalleCierreCaja.getInterfazContableProceso() != null) {
/* 130:180 */         throw new ExcepcionAS2Financiero("msg_error_anular");
/* 131:    */       }
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   public List<DetalleFormaCobro> obtenerListaDetalleCierreCaja(int idOrganizacion, String nombreUsuario, Integer idCaja)
/* 136:    */   {
/* 137:192 */     return this.cierreCajaDao.obtenerListaDetalleCierreCaja(idOrganizacion, nombreUsuario, idCaja);
/* 138:    */   }
/* 139:    */   
/* 140:    */   public List<AnticipoCliente> obtenerListaDetalleCierreCajaAC(int idOrganizacion, String nombreUsuario, Integer idCaja)
/* 141:    */   {
/* 142:202 */     return this.cierreCajaDao.obtenerListaDetalleCierreCajaAC(idOrganizacion, nombreUsuario, idCaja);
/* 143:    */   }
/* 144:    */   
/* 145:    */   private void validar(CierreCaja cierreCaja)
/* 146:    */     throws ExcepcionAS2
/* 147:    */   {
/* 148:207 */     if (cierreCaja.getListaDetalleCierreCaja() != null) {
/* 149:208 */       for (DetalleCierreCaja detalleCierreCaja : cierreCaja.getListaDetalleCierreCaja()) {
/* 150:209 */         if (detalleCierreCaja.getDetalleFormaCobro() != null)
/* 151:    */         {
/* 152:210 */           if (this.cierreCajaDao.isCierreCaja(detalleCierreCaja.getDetalleFormaCobro())) {
/* 153:211 */             throw new ExcepcionAS2("msg_detalle_cierre_caja", DocumentoBase.CIERRE_CAJA.getNombre());
/* 154:    */           }
/* 155:    */         }
/* 156:214 */         else if (this.cierreCajaDao.isCierreCaja(detalleCierreCaja.getAnticipoCliente())) {
/* 157:215 */           throw new ExcepcionAS2("msg_detalle_cierre_caja", DocumentoBase.CIERRE_CAJA.getNombre());
/* 158:    */         }
/* 159:    */       }
/* 160:    */     }
/* 161:    */   }
/* 162:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.caja.procesos.servicio.impl.ServicioCierreCajaImpl
 * JD-Core Version:    0.7.0.1
 */