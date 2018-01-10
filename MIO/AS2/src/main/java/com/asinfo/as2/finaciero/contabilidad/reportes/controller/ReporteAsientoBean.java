/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioFacturaProveedor;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.entities.Asiento;
/*   6:    */ import com.asinfo.as2.entities.Banco;
/*   7:    */ import com.asinfo.as2.entities.CuentaBancaria;
/*   8:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   9:    */ import com.asinfo.as2.entities.FormaPago;
/*  10:    */ import com.asinfo.as2.entities.MovimientoBancario;
/*  11:    */ import com.asinfo.as2.entities.TipoAsiento;
/*  12:    */ import com.asinfo.as2.entities.sri.FacturaProveedorSRI;
/*  13:    */ import com.asinfo.as2.enumeraciones.TipoReporteAsiento;
/*  14:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaProveedorSRI;
/*  15:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*  16:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioMovimientoBancario;
/*  17:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  18:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  19:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  20:    */ import java.io.IOException;
/*  21:    */ import java.math.BigDecimal;
/*  22:    */ import java.util.ArrayList;
/*  23:    */ import java.util.List;
/*  24:    */ import java.util.Map;
/*  25:    */ import javax.ejb.EJB;
/*  26:    */ import javax.faces.bean.ManagedBean;
/*  27:    */ import javax.faces.bean.RequestScoped;
/*  28:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  29:    */ import net.sf.jasperreports.engine.JRException;
/*  30:    */ import org.apache.log4j.Logger;
/*  31:    */ 
/*  32:    */ @ManagedBean
/*  33:    */ @RequestScoped
/*  34:    */ public class ReporteAsientoBean
/*  35:    */   extends AbstractBaseReportBean
/*  36:    */ {
/*  37:    */   private static final long serialVersionUID = -7757130645866515505L;
/*  38:    */   @EJB
/*  39:    */   private ServicioAsiento servicioAsiento;
/*  40:    */   @EJB
/*  41:    */   private ServicioMovimientoBancario servicioMovimientoBancario;
/*  42:    */   @EJB
/*  43:    */   private ServicioFacturaProveedorSRI servicioFacturaProveedorSRI;
/*  44:    */   @EJB
/*  45:    */   private ServicioFacturaProveedor servicioFacturaProveedor;
/*  46:    */   private Asiento asiento;
/*  47: 62 */   private Boolean resumido = Boolean.valueOf(false);
/*  48:    */   
/*  49:    */   public String execute()
/*  50:    */   {
/*  51:    */     try
/*  52:    */     {
/*  53: 67 */       setearResumen();
/*  54: 68 */       super.prepareReport();
/*  55:    */     }
/*  56:    */     catch (JRException e)
/*  57:    */     {
/*  58: 72 */       e.printStackTrace();
/*  59:    */     }
/*  60:    */     catch (IOException e)
/*  61:    */     {
/*  62: 75 */       e.printStackTrace();
/*  63:    */     }
/*  64: 78 */     return "";
/*  65:    */   }
/*  66:    */   
/*  67:    */   protected Map<String, Object> getReportParameters()
/*  68:    */   {
/*  69: 89 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  70: 90 */     reportParameters.put("ReportTitle", getLanguageController().getMensaje("msg_reporte_asiento_contable_titulo"));
/*  71:    */     String beneficiario;
/*  72:    */     String valor;
/*  73:    */     String numeroCheque;
/*  74:    */     String cuentaBancaria;
/*  75:    */     int cantidad;
/*  76: 92 */     if (getAsiento().getTipoAsiento().getTipoReporteAsiento() == TipoReporteAsiento.EGRESO)
/*  77:    */     {
/*  78: 94 */       StringBuffer numeroFacturas = new StringBuffer("");
/*  79: 95 */       List<FacturaProveedorSRI> listaFacturas = this.servicioFacturaProveedorSRI.obtenerFacturasPorEgresoPago(getAsiento().getIdAsiento());
/*  80: 96 */       for (FacturaProveedorSRI facturaProveedorSRI : listaFacturas) {
/*  81: 97 */         numeroFacturas.append(facturaProveedorSRI.getNumeroFactura()).append(" ");
/*  82:    */       }
/*  83:102 */       reportParameters.put("p_numeroFactura", numeroFacturas.toString());
/*  84:103 */       Object lista = this.servicioMovimientoBancario.getListaMovimientoBancarioPorAsiento(getAsiento().getId());
/*  85:    */       
/*  86:    */ 
/*  87:106 */       beneficiario = "";
/*  88:107 */       valor = "";
/*  89:108 */       numeroCheque = "";
/*  90:109 */       cuentaBancaria = "";
/*  91:110 */       cantidad = 0;
/*  92:112 */       for (MovimientoBancario mb : (List)lista)
/*  93:    */       {
/*  94:113 */         if (cantidad < 1)
/*  95:    */         {
/*  96:114 */           reportParameters.put("p_numeroCheque", mb.getDocumentoReferencia());
/*  97:115 */           reportParameters.put("p_cuentaBancaria", mb.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getNombre() + " " + mb
/*  98:116 */             .getCuentaBancariaOrganizacion().getCuentaBancaria().getNumero());
/*  99:117 */           reportParameters.put("p_valorCheque", mb.getValor().abs());
/* 100:118 */           reportParameters.put("p_beneficiario", mb.getBeneficiario());
/* 101:119 */           reportParameters.put("p_cuentaBancariaOrganizacion", mb.getCuentaBancariaOrganizacion().getCuentaBancaria().getNumero());
/* 102:120 */           reportParameters.put("p_banco", mb.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getNombre());
/* 103:121 */           reportParameters.put("p_forma_pago", mb.getFormaPago().getNombre());
/* 104:122 */           cantidad++;
/* 105:    */         }
/* 106:124 */         beneficiario = beneficiario + mb.getBeneficiario() + '\n';
/* 107:125 */         reportParameters.put("p_beneficiarioMovimientoBancario", beneficiario);
/* 108:126 */         numeroCheque = numeroCheque + mb.getDocumentoReferencia() + '\n';
/* 109:127 */         reportParameters.put("p_numeroChequeMovimientoBancario", numeroCheque);
/* 110:    */         
/* 111:129 */         cuentaBancaria = cuentaBancaria + mb.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getNombre() + " " + mb.getCuentaBancariaOrganizacion().getCuentaBancaria().getNumero() + '\n';
/* 112:130 */         reportParameters.put("p_cuentaBancariaMovimientoInventario", cuentaBancaria);
/* 113:131 */         valor = valor + mb.getValor().abs().toString() + '\n';
/* 114:132 */         reportParameters.put("p_valorChequeMovimientoBancario", valor);
/* 115:    */       }
/* 116:    */     }
/* 117:137 */     if (getAsiento().getTipoAsiento().getTipoReporteAsiento() == TipoReporteAsiento.DIARIO_R)
/* 118:    */     {
/* 119:140 */       Object[] datosFactura = this.servicioFacturaProveedor.getDatosFacturaImpresionAsiento(getAsiento().getIdAsiento());
/* 120:142 */       if ((datosFactura != null) && (datosFactura.length > 0))
/* 121:    */       {
/* 122:143 */         if (datosFactura[0] != null) {
/* 123:144 */           reportParameters.put("p_numeroFactura", datosFactura[0].toString());
/* 124:    */         }
/* 125:147 */         reportParameters.put("p_identificacionProveedor", datosFactura[1].toString());
/* 126:148 */         reportParameters.put("p_proveedor", datosFactura[2] != null ? datosFactura[2].toString() : "");
/* 127:149 */         reportParameters.put("p_direccionProveedor", datosFactura[3] != null ? datosFactura[3].toString() : "");
/* 128:150 */         if (datosFactura[4] != null) {
/* 129:151 */           reportParameters.put("p_numeroRetencion", datosFactura[4].toString());
/* 130:    */         }
/* 131:    */       }
/* 132:    */     }
/* 133:156 */     return reportParameters;
/* 134:    */   }
/* 135:    */   
/* 136:    */   protected JRDataSource getJRDataSource()
/* 137:    */   {
/* 138:167 */     List listaDatosReporte = new ArrayList();
/* 139:168 */     JRDataSource ds = null;
/* 140:169 */     String[] fields = { "numero", "tipoAsiento", "concepto", "fecha", "codigoCuenta", "nombreCuentaContable", "codigoCentroCosto", "nombreCentroCosto", "debe", "haber", "descripcion", "estado", "sucursal", "codigo_d1", "nombre_d1", "codigo_d2", "nombre_d2", "codigo_d3", "nombre_d3", "codigo_d4", "nombre_d4", "codigo_d5", "nombre_d5", "f_usuarioCreacion", "f_descripcion2", "f_codigoCuentaAlterno", "f_sucursal", "f_concepto2" };
/* 141:    */     
/* 142:    */ 
/* 143:    */ 
/* 144:173 */     boolean indicadorCentroCostos = false;
/* 145:    */     try
/* 146:    */     {
/* 147:176 */       if (getAsiento().getTipoAsiento().getTipoReporteAsiento() == TipoReporteAsiento.DIARIO_R) {
/* 148:177 */         indicadorCentroCostos = true;
/* 149:    */       } else {
/* 150:179 */         indicadorCentroCostos = false;
/* 151:    */       }
/* 152:181 */       listaDatosReporte = this.servicioAsiento.getReporteAsiento(getAsiento(), indicadorCentroCostos, isResumido());
/* 153:182 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 154:    */     }
/* 155:    */     catch (ExcepcionAS2Financiero e)
/* 156:    */     {
/* 157:184 */       LOG.info("Error " + e);
/* 158:185 */       e.printStackTrace();
/* 159:    */     }
/* 160:187 */     return ds;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setearResumen()
/* 164:    */   {
/* 165:191 */     setResumido(Boolean.valueOf(false));
/* 166:    */   }
/* 167:    */   
/* 168:    */   protected String getCompileFileName()
/* 169:    */   {
/* 170:202 */     String compileFileName = null;
/* 171:203 */     TipoAsiento tipoAsiento = getAsiento().getTipoAsiento();
/* 172:204 */     if (isResumido().booleanValue())
/* 173:    */     {
/* 174:205 */       if ((tipoAsiento.getReporteResumen() != null) && (!tipoAsiento.getReporteResumen().isEmpty())) {
/* 175:206 */         compileFileName = tipoAsiento.getReporteResumen();
/* 176:    */       } else {
/* 177:208 */         compileFileName = tipoAsiento.getReporte();
/* 178:    */       }
/* 179:    */     }
/* 180:    */     else {
/* 181:211 */       compileFileName = tipoAsiento.getReporte();
/* 182:    */     }
/* 183:213 */     if ((compileFileName == null) || (compileFileName.isEmpty())) {
/* 184:214 */       compileFileName = "diario";
/* 185:    */     }
/* 186:216 */     return compileFileName;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public Asiento getAsiento()
/* 190:    */   {
/* 191:220 */     if (this.asiento == null) {
/* 192:221 */       this.asiento = new Asiento();
/* 193:    */     }
/* 194:224 */     return this.asiento;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void setAsiento(Asiento asiento)
/* 198:    */   {
/* 199:228 */     this.asiento = asiento;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public Boolean isResumido()
/* 203:    */   {
/* 204:232 */     return this.resumido;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void setResumido(Boolean resumido)
/* 208:    */   {
/* 209:236 */     this.resumido = resumido;
/* 210:    */   }
/* 211:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.reportes.controller.ReporteAsientoBean
 * JD-Core Version:    0.7.0.1
 */