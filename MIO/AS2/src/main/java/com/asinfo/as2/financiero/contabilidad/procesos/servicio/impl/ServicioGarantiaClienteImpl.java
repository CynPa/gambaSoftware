/*   1:    */ package com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.DireccionEmpresaDao;
/*   4:    */ import com.asinfo.as2.dao.DocumentoDao;
/*   5:    */ import com.asinfo.as2.dao.GarantiaClienteDao;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioCondicionPago;
/*   7:    */ import com.asinfo.as2.entities.CondicionPago;
/*   8:    */ import com.asinfo.as2.entities.DireccionEmpresa;
/*   9:    */ import com.asinfo.as2.entities.Empresa;
/*  10:    */ import com.asinfo.as2.entities.FacturaCliente;
/*  11:    */ import com.asinfo.as2.entities.GarantiaCliente;
/*  12:    */ import com.asinfo.as2.entities.Organizacion;
/*  13:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  14:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  15:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioGarantiaCliente;
/*  16:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  17:    */ import com.asinfo.as2.servicio.AbstractServicioAS2Financiero;
/*  18:    */ import com.asinfo.as2.util.AppUtil;
/*  19:    */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  20:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  21:    */ import java.math.BigDecimal;
/*  22:    */ import java.util.Date;
/*  23:    */ import java.util.HashMap;
/*  24:    */ import java.util.List;
/*  25:    */ import java.util.Map;
/*  26:    */ import javax.ejb.EJB;
/*  27:    */ import javax.ejb.SessionContext;
/*  28:    */ import javax.ejb.Stateless;
/*  29:    */ 
/*  30:    */ @Stateless
/*  31:    */ public class ServicioGarantiaClienteImpl
/*  32:    */   extends AbstractServicioAS2Financiero
/*  33:    */   implements ServicioGarantiaCliente
/*  34:    */ {
/*  35:    */   @EJB
/*  36:    */   private GarantiaClienteDao garantiaClienteDao;
/*  37:    */   @EJB
/*  38:    */   private DocumentoDao documentoDao;
/*  39:    */   @EJB
/*  40:    */   private ServicioFacturaCliente servicioFacturaCliente;
/*  41:    */   @EJB
/*  42:    */   private DireccionEmpresaDao direccionEmpresaDao;
/*  43:    */   @EJB
/*  44:    */   private ServicioCondicionPago servicioCondicionPago;
/*  45:    */   
/*  46:    */   public void guardar(GarantiaCliente garantiaCliente)
/*  47:    */     throws ExcepcionAS2, ExcepcionAS2Financiero, ExcepcionAS2Ventas
/*  48:    */   {
/*  49:    */     try
/*  50:    */     {
/*  51: 72 */       if (garantiaCliente.getTraIndicadorPersonaProtestada() == 1)
/*  52:    */       {
/*  53: 73 */         FacturaCliente facturaCliente = generaFactura(garantiaCliente);
/*  54: 74 */         garantiaCliente.setFacturaCliente(facturaCliente);
/*  55: 75 */         this.garantiaClienteDao.guardar(garantiaCliente);
/*  56:    */       }
/*  57:    */       else
/*  58:    */       {
/*  59: 77 */         this.garantiaClienteDao.guardar(garantiaCliente);
/*  60:    */       }
/*  61:    */     }
/*  62:    */     catch (ExcepcionAS2Financiero e)
/*  63:    */     {
/*  64: 81 */       this.context.setRollbackOnly();
/*  65: 82 */       e.printStackTrace();
/*  66: 83 */       throw e;
/*  67:    */     }
/*  68:    */     catch (ExcepcionAS2Ventas e)
/*  69:    */     {
/*  70: 86 */       this.context.setRollbackOnly();
/*  71: 87 */       e.printStackTrace();
/*  72: 88 */       throw e;
/*  73:    */     }
/*  74:    */     catch (ExcepcionAS2 e)
/*  75:    */     {
/*  76: 91 */       this.context.setRollbackOnly();
/*  77: 92 */       e.printStackTrace();
/*  78: 93 */       throw e;
/*  79:    */     }
/*  80:    */     catch (Exception e)
/*  81:    */     {
/*  82: 95 */       this.context.setRollbackOnly();
/*  83: 96 */       e.printStackTrace();
/*  84: 97 */       throw new ExcepcionAS2(e);
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void eliminar(GarantiaCliente garantiaCliente)
/*  89:    */   {
/*  90:108 */     this.garantiaClienteDao.eliminar(garantiaCliente);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public GarantiaCliente buscarPorId(Integer id)
/*  94:    */   {
/*  95:119 */     return (GarantiaCliente)this.garantiaClienteDao.buscarPorId(id);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public List<GarantiaCliente> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  99:    */   {
/* 100:130 */     return this.garantiaClienteDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public int contarPorCriterio(Map<String, String> filters)
/* 104:    */   {
/* 105:140 */     return this.garantiaClienteDao.contarPorCriterio(filters);
/* 106:    */   }
/* 107:    */   
/* 108:    */   private FacturaCliente generaFactura(GarantiaCliente garantiaCliente)
/* 109:    */     throws Exception, ExcepcionAS2, ExcepcionAS2Ventas
/* 110:    */   {
/* 111:151 */     FacturaCliente facturaCliente = new FacturaCliente();
/* 112:152 */     facturaCliente.setAuditable(false);
/* 113:153 */     facturaCliente.setSucursal(AppUtil.getSucursal());
/* 114:154 */     facturaCliente.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 115:155 */     facturaCliente.setDocumento(this.documentoDao.buscarPorId(Integer.valueOf(garantiaCliente.getTraIdOpcionPersonaProtestada())));
/* 116:156 */     facturaCliente.setEmpresa(garantiaCliente.getEmpresa());
/* 117:157 */     facturaCliente.setAsiento(null);
/* 118:158 */     Map<String, String> filters = new HashMap();
/* 119:159 */     filters.put("predeterminado", "true");
/* 120:160 */     List<CondicionPago> listaCondicionPago = this.servicioCondicionPago.obtenerListaCombo("nombre", false, filters);
/* 121:161 */     if (listaCondicionPago.isEmpty()) {
/* 122:162 */       throw new ExcepcionAS2("msg_info_creacion_cliente_agil_condicion_pago");
/* 123:    */     }
/* 124:164 */     facturaCliente.setCondicionPago((CondicionPago)listaCondicionPago.get(0));
/* 125:    */     
/* 126:166 */     facturaCliente.setDireccionEmpresa((DireccionEmpresa)this.direccionEmpresaDao.obtenerListaDireccionesPrincipales(garantiaCliente.getEmpresa().getId()).get(0));
/* 127:167 */     facturaCliente.setNumero("");
/* 128:168 */     facturaCliente.setFecha(new Date());
/* 129:169 */     facturaCliente.setTotal(garantiaCliente.getValorProtestado());
/* 130:170 */     facturaCliente.setDescuento(BigDecimal.ZERO);
/* 131:171 */     facturaCliente.setImpuesto(BigDecimal.ZERO);
/* 132:172 */     facturaCliente.setNumeroCuotas(1);
/* 133:173 */     facturaCliente.setDescripcion(facturaCliente.getDocumento() + " # " + garantiaCliente.getNumero());
/* 134:174 */     facturaCliente.setEstado(Estado.ELABORADO);
/* 135:175 */     this.servicioFacturaCliente.generarCuentaPorCobrar(facturaCliente);
/* 136:176 */     this.servicioFacturaCliente.guardar(facturaCliente);
/* 137:177 */     return facturaCliente;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public List<GarantiaCliente> buscaListaChequesPosfechados(int idEmpresa)
/* 141:    */   {
/* 142:182 */     return this.garantiaClienteDao.buscaListaChequesPosfechados(idEmpresa);
/* 143:    */   }
/* 144:    */   
/* 145:    */   public BigDecimal obtenerSaldoChequePosfechado(int idCliente, Date fechaHasta)
/* 146:    */   {
/* 147:194 */     return this.garantiaClienteDao.obtenerSaldoChequePosfechado(idCliente, fechaHasta);
/* 148:    */   }
/* 149:    */   
/* 150:    */   public List<GarantiaCliente> getListaGarantiaClienteCobro(int idCobro)
/* 151:    */   {
/* 152:204 */     return this.garantiaClienteDao.getListaGarantiaClienteCobro(idCobro);
/* 153:    */   }
/* 154:    */   
/* 155:    */   public GarantiaCliente cargarDetalle(int idGarantiaCliente)
/* 156:    */   {
/* 157:212 */     return this.garantiaClienteDao.cargarDetalle(idGarantiaCliente);
/* 158:    */   }
/* 159:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl.ServicioGarantiaClienteImpl
 * JD-Core Version:    0.7.0.1
 */