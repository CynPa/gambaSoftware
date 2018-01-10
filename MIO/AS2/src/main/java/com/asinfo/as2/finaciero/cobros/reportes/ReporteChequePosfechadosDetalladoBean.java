/*   1:    */ package com.asinfo.as2.finaciero.cobros.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   6:    */ import com.asinfo.as2.entities.Empresa;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  10:    */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioCobro;
/*  11:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  12:    */ import com.asinfo.as2.util.AppUtil;
/*  13:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  14:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  15:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  16:    */ import java.io.IOException;
/*  17:    */ import java.io.PrintStream;
/*  18:    */ import java.util.ArrayList;
/*  19:    */ import java.util.Calendar;
/*  20:    */ import java.util.Date;
/*  21:    */ import java.util.HashMap;
/*  22:    */ import java.util.List;
/*  23:    */ import java.util.Map;
/*  24:    */ import javax.ejb.EJB;
/*  25:    */ import javax.faces.bean.ManagedBean;
/*  26:    */ import javax.faces.bean.ViewScoped;
/*  27:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  28:    */ import net.sf.jasperreports.engine.JRException;
/*  29:    */ import org.apache.log4j.Logger;
/*  30:    */ 
/*  31:    */ @ManagedBean
/*  32:    */ @ViewScoped
/*  33:    */ public class ReporteChequePosfechadosDetalladoBean
/*  34:    */   extends AbstractBaseReportBean
/*  35:    */ {
/*  36:    */   private static final long serialVersionUID = 1L;
/*  37:    */   @EJB
/*  38:    */   private ServicioCobro servicioCobro;
/*  39:    */   @EJB
/*  40:    */   private ServicioEmpresa servicioEmpresa;
/*  41:    */   @EJB
/*  42:    */   private ServicioUsuario servicioUsuario;
/*  43:    */   @EJB
/*  44:    */   private ServicioSucursal servicioSucursal;
/*  45:    */   private Date fechaDesde;
/*  46:    */   private Date fechaHasta;
/*  47:    */   private Empresa empresa;
/*  48:    */   private Sucursal sucursal;
/*  49:    */   private List<Sucursal> listaSucursal;
/*  50:    */   private EntidadUsuario vendedor;
/*  51:    */   private List<EntidadUsuario> listaVendedor;
/*  52:    */   
/*  53:    */   public ReporteChequePosfechadosDetalladoBean()
/*  54:    */   {
/*  55: 76 */     Calendar cal = Calendar.getInstance();
/*  56: 77 */     this.fechaDesde = FuncionesUtiles.getFecha(1, cal.get(2) + 1, cal.get(1));
/*  57: 78 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/*  58:    */   }
/*  59:    */   
/*  60:    */   protected String getCompileFileName()
/*  61:    */   {
/*  62: 89 */     return "reporteChequePosfechadosDetallado";
/*  63:    */   }
/*  64:    */   
/*  65:    */   protected Map<String, Object> getReportParameters()
/*  66:    */   {
/*  67:101 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  68:102 */     reportParameters.put("fechaDesde", this.fechaDesde);
/*  69:103 */     reportParameters.put("fechaHasta", this.fechaHasta);
/*  70:104 */     reportParameters.put("p_sucursal", this.sucursal == null ? "" : this.sucursal.getNombre());
/*  71:105 */     reportParameters.put("p_vendedor", this.vendedor == null ? "" : this.vendedor.getNombreUsuario());
/*  72:    */     
/*  73:107 */     return reportParameters;
/*  74:    */   }
/*  75:    */   
/*  76:    */   protected JRDataSource getJRDataSource()
/*  77:    */   {
/*  78:118 */     List<Object[]> listaDatosReporte = new ArrayList();
/*  79:    */     
/*  80:    */ 
/*  81:121 */     int idOrganizacion = AppUtil.getOrganizacion().getId();
/*  82:    */     
/*  83:123 */     listaDatosReporte = this.servicioCobro.reporteChequePosfechadoDetallado(idOrganizacion, this.sucursal, this.empresa, this.fechaDesde, this.fechaHasta, this.vendedor);
/*  84:125 */     for (Object[] string : listaDatosReporte) {
/*  85:126 */       System.out.println(string[10] + " " + string[11] + " " + string[12] + " " + string[13]);
/*  86:    */     }
/*  87:128 */     String[] fields = { "f_idCuentaPorCobrar", "f_documento", "f_identificacion", "f_nombreFiscal", "f_ciudad", "f_telefono", "f_numeroFactura", "f_emision", "f_vencimiento", "f_vendedor", "f_valor", "f_saldo", "f_telefono2", "f_direccion", "f_banco", "f_fechaCheque", "f_numeroCheque", "f_valorCheque", "f_vencido" };
/*  88:    */     
/*  89:    */ 
/*  90:    */ 
/*  91:132 */     return new QueryResultDataSource(listaDatosReporte, fields);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String execute()
/*  95:    */   {
/*  96:    */     try
/*  97:    */     {
/*  98:143 */       super.prepareReport();
/*  99:    */     }
/* 100:    */     catch (JRException e)
/* 101:    */     {
/* 102:146 */       LOG.info("Error JRException");
/* 103:147 */       e.printStackTrace();
/* 104:148 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 105:    */     }
/* 106:    */     catch (IOException e)
/* 107:    */     {
/* 108:150 */       LOG.info("Error IOException");
/* 109:151 */       e.printStackTrace();
/* 110:152 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 111:    */     }
/* 112:155 */     return "";
/* 113:    */   }
/* 114:    */   
/* 115:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 116:    */   {
/* 117:165 */     return this.servicioEmpresa.autocompletarClientes(consulta);
/* 118:    */   }
/* 119:    */   
/* 120:    */   public Date getFechaHasta()
/* 121:    */   {
/* 122:174 */     return this.fechaHasta;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setFechaHasta(Date fechaHasta)
/* 126:    */   {
/* 127:184 */     this.fechaHasta = fechaHasta;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public Empresa getEmpresa()
/* 131:    */   {
/* 132:188 */     return this.empresa;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setEmpresa(Empresa empresa)
/* 136:    */   {
/* 137:192 */     this.empresa = empresa;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public Date getFechaDesde()
/* 141:    */   {
/* 142:201 */     return this.fechaDesde;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setFechaDesde(Date fechaDesde)
/* 146:    */   {
/* 147:211 */     this.fechaDesde = fechaDesde;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public Sucursal getSucursal()
/* 151:    */   {
/* 152:218 */     return this.sucursal;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setSucursal(Sucursal sucursal)
/* 156:    */   {
/* 157:226 */     this.sucursal = sucursal;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public List<Sucursal> getListaSucursal()
/* 161:    */   {
/* 162:233 */     if (this.listaSucursal == null)
/* 163:    */     {
/* 164:234 */       Map<String, String> filters = new HashMap();
/* 165:235 */       filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 166:236 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, filters);
/* 167:    */     }
/* 168:238 */     return this.listaSucursal;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setListaSucursal(List<Sucursal> listaSucursal)
/* 172:    */   {
/* 173:246 */     this.listaSucursal = listaSucursal;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public List<EntidadUsuario> getListaVendedor()
/* 177:    */   {
/* 178:253 */     this.listaVendedor = new ArrayList();
/* 179:254 */     this.listaVendedor = this.servicioUsuario.obtenerListaAgenteComercial(AppUtil.getOrganizacion().getId());
/* 180:255 */     return this.listaVendedor;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setListaVendedor(List<EntidadUsuario> listaVendedor)
/* 184:    */   {
/* 185:263 */     this.listaVendedor = listaVendedor;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public EntidadUsuario getVendedor()
/* 189:    */   {
/* 190:267 */     return this.vendedor;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setVendedor(EntidadUsuario vendedor)
/* 194:    */   {
/* 195:271 */     this.vendedor = vendedor;
/* 196:    */   }
/* 197:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.reportes.ReporteChequePosfechadosDetalladoBean
 * JD-Core Version:    0.7.0.1
 */