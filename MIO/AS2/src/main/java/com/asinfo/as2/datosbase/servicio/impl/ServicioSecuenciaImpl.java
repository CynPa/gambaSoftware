/*   1:    */ package com.asinfo.as2.datosbase.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.SecuenciaDao;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuenciaRemote;
/*   7:    */ import com.asinfo.as2.entities.Documento;
/*   8:    */ import com.asinfo.as2.entities.Secuencia;
/*   9:    */ import com.asinfo.as2.entities.TipoAsiento;
/*  10:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  11:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioTipoAsiento;
/*  12:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  13:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  14:    */ import java.util.Date;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Map;
/*  17:    */ import javax.ejb.EJB;
/*  18:    */ import javax.ejb.Stateless;
/*  19:    */ 
/*  20:    */ @Stateless
/*  21:    */ public class ServicioSecuenciaImpl
/*  22:    */   implements ServicioSecuencia, ServicioSecuenciaRemote
/*  23:    */ {
/*  24:    */   @EJB
/*  25:    */   private SecuenciaDao secuenciaDao;
/*  26:    */   @EJB
/*  27:    */   private ServicioDocumento servicioDocumento;
/*  28:    */   @EJB
/*  29:    */   private ServicioTipoAsiento servicioTipoAsiento;
/*  30:    */   
/*  31:    */   public void guardar(Secuencia secuencia)
/*  32:    */     throws ExcepcionAS2Financiero
/*  33:    */   {
/*  34: 54 */     validar(secuencia);
/*  35:    */     
/*  36: 56 */     this.secuenciaDao.guardar(secuencia);
/*  37:    */   }
/*  38:    */   
/*  39:    */   private void validar(Secuencia secuencia)
/*  40:    */     throws ExcepcionAS2Financiero
/*  41:    */   {
/*  42: 64 */     int numero = secuencia.getNumero() + 1;
/*  43: 66 */     if ((numero < secuencia.getDesde()) || (numero > secuencia.getHasta())) {
/*  44: 67 */       throw new ExcepcionAS2Financiero("msg_numero_secuencia_no_valido");
/*  45:    */     }
/*  46: 69 */     if (secuencia.getFechaHasta().compareTo(secuencia.getFechaDesde()) == -1) {
/*  47: 70 */       throw new ExcepcionAS2Financiero("msg_error_fecha_hasta");
/*  48:    */     }
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void eliminar(Secuencia secuencia)
/*  52:    */   {
/*  53: 82 */     this.secuenciaDao.eliminar(secuencia);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int contarPorCriterio(Map<String, String> filters)
/*  57:    */   {
/*  58: 92 */     return this.secuenciaDao.contarPorCriterio(filters);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Secuencia buscarPorId(Integer id)
/*  62:    */   {
/*  63:102 */     return (Secuencia)this.secuenciaDao.buscarPorId(id);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public String obtenerSecuencia(Secuencia secuencia, Date fecha)
/*  67:    */     throws ExcepcionAS2
/*  68:    */   {
/*  69:114 */     fecha = FuncionesUtiles.setAtributoFecha(secuencia.getFechaDesde());
/*  70:    */     
/*  71:116 */     String numero = "";
/*  72:117 */     if (((fecha.after(secuencia.getFechaDesde())) || (fecha.equals(secuencia.getFechaDesde()))) && (
/*  73:118 */       (fecha.before(secuencia.getFechaHasta())) || (fecha.equals(secuencia.getFechaHasta()))))
/*  74:    */     {
/*  75:120 */       secuencia.setNumero(secuencia.getNumero() + 1);
/*  76:121 */       numero = secuencia.getPrefijo();
/*  77:    */       
/*  78:123 */       int completarHasta = secuencia.getLongitud() - (secuencia.getPrefijo().length() + String.valueOf(secuencia.getNumero()).length() + secuencia.getSufijo().length());
/*  79:124 */       for (int i = 0; i < completarHasta; i++) {
/*  80:125 */         numero = numero + "0";
/*  81:    */       }
/*  82:127 */       numero = numero + secuencia.getNumero() + secuencia.getSufijo();
/*  83:128 */       return numero;
/*  84:    */     }
/*  85:131 */     throw new ExcepcionAS2("msg_secuencia_no_encontrada");
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String obtenerSecuenciaDocumento(int idDocumento, Date fecha)
/*  89:    */     throws ExcepcionAS2
/*  90:    */   {
/*  91:142 */     Documento documento = this.servicioDocumento.buscarPorId(Integer.valueOf(idDocumento));
/*  92:    */     
/*  93:144 */     String numeroSecuencia = obtenerSecuencia(documento.getSecuencia(), fecha);
/*  94:145 */     actualizarSecuencia(documento.getSecuencia(), numeroSecuencia);
/*  95:    */     
/*  96:147 */     return numeroSecuencia;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String obtenerSecuenciaTipoAsiento(int idTipoAsiento, Date fecha)
/* 100:    */     throws ExcepcionAS2
/* 101:    */   {
/* 102:158 */     TipoAsiento tipoAsiento = this.servicioTipoAsiento.buscarPorId(Integer.valueOf(idTipoAsiento));
/* 103:159 */     if (tipoAsiento == null) {
/* 104:160 */       throw new ExcepcionAS2("msg_error_tipo_asiento_no_encontrado");
/* 105:    */     }
/* 106:162 */     String numeroSecuencia = obtenerSecuencia(tipoAsiento.getSecuencia(), fecha);
/* 107:163 */     actualizarSecuencia(tipoAsiento.getSecuencia(), numeroSecuencia);
/* 108:    */     
/* 109:165 */     return numeroSecuencia;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public List<Secuencia> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 113:    */   {
/* 114:175 */     return this.secuenciaDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public List<Secuencia> obtenerListaCombo()
/* 118:    */   {
/* 119:185 */     return obtenerListaCombo("nombre", true, null);
/* 120:    */   }
/* 121:    */   
/* 122:    */   public List<Secuencia> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 123:    */   {
/* 124:190 */     return this.secuenciaDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void actualizarSecuencia(Secuencia secuencia, String strNumero)
/* 128:    */   {
/* 129:204 */     String prefijo = secuencia.getPrefijo();
/* 130:205 */     String sufijo = secuencia.getSufijo();
/* 131:207 */     if (!prefijo.isEmpty()) {
/* 132:208 */       strNumero = strNumero.substring(prefijo.length());
/* 133:    */     }
/* 134:210 */     if (!sufijo.isEmpty()) {
/* 135:211 */       strNumero = strNumero.substring(0, strNumero.length() - sufijo.length());
/* 136:    */     }
/* 137:214 */     int numero = Integer.parseInt(strNumero);
/* 138:215 */     this.secuenciaDao.actualizarSecuencia(secuencia, numero);
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void detach(Secuencia secuencia)
/* 142:    */   {
/* 143:226 */     this.secuenciaDao.detach(secuencia);
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void flush()
/* 147:    */   {
/* 148:231 */     this.secuenciaDao.flush();
/* 149:    */   }
/* 150:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.impl.ServicioSecuenciaImpl
 * JD-Core Version:    0.7.0.1
 */