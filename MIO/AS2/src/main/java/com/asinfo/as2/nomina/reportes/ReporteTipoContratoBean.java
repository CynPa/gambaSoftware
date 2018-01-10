/*   1:    */ package com.asinfo.as2.nomina.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.CargoEmpleado;
/*   5:    */ import com.asinfo.as2.entities.Ciudad;
/*   6:    */ import com.asinfo.as2.entities.DireccionEmpresa;
/*   7:    */ import com.asinfo.as2.entities.Empleado;
/*   8:    */ import com.asinfo.as2.entities.Empresa;
/*   9:    */ import com.asinfo.as2.entities.HistoricoEmpleado;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.entities.Sucursal;
/*  12:    */ import com.asinfo.as2.entities.TipoContrato;
/*  13:    */ import com.asinfo.as2.entities.Ubicacion;
/*  14:    */ import com.asinfo.as2.enumeraciones.VariablesContrato;
/*  15:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioHistoricoEmpleado;
/*  16:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioRubroEmpleado;
/*  17:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  18:    */ import com.asinfo.as2.util.AppUtil;
/*  19:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  20:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  21:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  22:    */ import java.io.IOException;
/*  23:    */ import java.math.BigDecimal;
/*  24:    */ import java.util.ArrayList;
/*  25:    */ import java.util.List;
/*  26:    */ import java.util.Map;
/*  27:    */ import javax.ejb.EJB;
/*  28:    */ import javax.faces.bean.ManagedBean;
/*  29:    */ import javax.faces.bean.RequestScoped;
/*  30:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  31:    */ import net.sf.jasperreports.engine.JRException;
/*  32:    */ import org.apache.log4j.Logger;
/*  33:    */ 
/*  34:    */ @ManagedBean
/*  35:    */ @RequestScoped
/*  36:    */ public class ReporteTipoContratoBean
/*  37:    */   extends AbstractBaseReportBean
/*  38:    */ {
/*  39:    */   private static final long serialVersionUID = -5571110678081092104L;
/*  40:    */   @EJB
/*  41:    */   private transient ServicioReporteNomina servicioReporteNomina;
/*  42:    */   @EJB
/*  43:    */   private transient ServicioHistoricoEmpleado servicioHistoricoEmpleado;
/*  44:    */   @EJB
/*  45:    */   private transient ServicioRubroEmpleado servicioRubroEmpleado;
/*  46:    */   @EJB
/*  47:    */   private transient ServicioGenerico<TipoContrato> servicioTipoContrato;
/*  48:    */   private HistoricoEmpleado historicoEmpleado;
/*  49:    */   private HistoricoEmpleado auxHistoricoEmpleado;
/*  50:    */   
/*  51:    */   protected JRDataSource getJRDataSource()
/*  52:    */   {
/*  53: 71 */     JRDataSource jrDataSource = null;
/*  54:    */     try
/*  55:    */     {
/*  56: 74 */       String textoReporte = this.servicioReporteNomina.getReporteTipoContratoPorId(this.auxHistoricoEmpleado.getEmpleado().getTipoContrato().getIdTipoContrato());
/*  57: 75 */       String textoReemplazado = reemplazarTexto(textoReporte, this.auxHistoricoEmpleado);
/*  58:    */       
/*  59: 77 */       List<Object[]> lista = new ArrayList();
/*  60: 78 */       Object[] objeto = new Object[1];
/*  61: 79 */       objeto[0] = textoReemplazado;
/*  62: 80 */       lista.add(objeto);
/*  63:    */       
/*  64: 82 */       String[] fields = { "f_mensaje" };
/*  65:    */       
/*  66: 84 */       jrDataSource = new QueryResultDataSource(lista, fields);
/*  67:    */     }
/*  68:    */     catch (Exception e)
/*  69:    */     {
/*  70: 87 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  71: 88 */       LOG.info("Error al cargar reporte de contrato " + e);
/*  72:    */     }
/*  73: 90 */     return jrDataSource;
/*  74:    */   }
/*  75:    */   
/*  76:    */   private String reemplazarTexto(String textoDato, HistoricoEmpleado auxHistoricoEmpleado)
/*  77:    */   {
/*  78: 94 */     String respuesta = textoDato;
/*  79: 95 */     respuesta = respuesta.replaceAll("strong", "b");
/*  80: 96 */     for (VariablesContrato variablesContrato : VariablesContrato.values()) {
/*  81: 97 */       switch (1.$SwitchMap$com$asinfo$as2$enumeraciones$VariablesContrato[variablesContrato.ordinal()])
/*  82:    */       {
/*  83:    */       case 1: 
/*  84:100 */         respuesta = respuesta.replaceAll(variablesContrato.getVariable(), FuncionesUtiles.convertidorFechaALetras(getHistoricoEmpleado().getFechaIngreso()));
/*  85:101 */         break;
/*  86:    */       case 2: 
/*  87:104 */         respuesta = respuesta.replaceAll(variablesContrato.getVariable(), AppUtil.getOrganizacion().getRazonSocial());
/*  88:105 */         break;
/*  89:    */       case 3: 
/*  90:108 */         respuesta = respuesta.replaceAll(variablesContrato.getVariable(), AppUtil.getOrganizacion().getRepresentanteLegal());
/*  91:109 */         break;
/*  92:    */       case 4: 
/*  93:112 */         respuesta = respuesta.replaceAll(variablesContrato.getVariable(), AppUtil.getSucursal().getUbicacion().getDireccionCompleta());
/*  94:113 */         break;
/*  95:    */       case 5: 
/*  96:116 */         respuesta = respuesta.replaceAll(variablesContrato.getVariable(), AppUtil.getOrganizacion().getIdentificacion());
/*  97:117 */         break;
/*  98:    */       case 6: 
/*  99:120 */         respuesta = respuesta.replaceAll(variablesContrato.getVariable(), AppUtil.getSucursal().getTelefono1());
/* 100:121 */         break;
/* 101:    */       case 7: 
/* 102:124 */         if (auxHistoricoEmpleado.getEmpleado().getEmpresa().getDirecciones().isEmpty()) {
/* 103:125 */           respuesta = respuesta.replaceAll(variablesContrato.getVariable(), "");
/* 104:    */         } else {
/* 105:127 */           respuesta = respuesta.replaceAll(variablesContrato.getVariable(), 
/* 106:128 */             ((DireccionEmpresa)auxHistoricoEmpleado.getEmpleado().getEmpresa().getDirecciones().get(0)).getCiudad().getNombre());
/* 107:    */         }
/* 108:130 */         break;
/* 109:    */       case 8: 
/* 110:133 */         respuesta = respuesta.replaceAll(variablesContrato.getVariable(), auxHistoricoEmpleado
/* 111:134 */           .getEmpleado().getApellidos() + " " + auxHistoricoEmpleado.getEmpleado().getNombres());
/* 112:135 */         break;
/* 113:    */       case 9: 
/* 114:138 */         respuesta = respuesta.replaceAll(variablesContrato.getVariable(), auxHistoricoEmpleado.getEmpleado().getEmpresa().getIdentificacion());
/* 115:139 */         break;
/* 116:    */       case 10: 
/* 117:142 */         respuesta = respuesta.replaceAll(variablesContrato.getVariable(), auxHistoricoEmpleado.getEmpleado().getCargoEmpleado().getNombre());
/* 118:143 */         break;
/* 119:    */       case 11: 
/* 120:146 */         respuesta = respuesta.replaceAll(variablesContrato.getVariable(), FuncionesUtiles.convertidorFechaALetras(auxHistoricoEmpleado.getFechaIngreso()));
/* 121:147 */         break;
/* 122:    */       case 12: 
/* 123:150 */         if (auxHistoricoEmpleado.getEmpleado().getEmpresa().getDirecciones().isEmpty()) {
/* 124:151 */           respuesta = respuesta.replaceAll(variablesContrato.getVariable(), "");
/* 125:    */         } else {
/* 126:153 */           respuesta = respuesta.replaceAll(variablesContrato.getVariable(), 
/* 127:154 */             ((DireccionEmpresa)auxHistoricoEmpleado.getEmpleado().getEmpresa().getDirecciones().get(0)).getDireccionCompleta());
/* 128:    */         }
/* 129:157 */         break;
/* 130:    */       case 13: 
/* 131:160 */         respuesta = respuesta.replaceAll(variablesContrato.getVariable(), this.servicioRubroEmpleado
/* 132:161 */           .obtenerSueldoPorEmpleado(auxHistoricoEmpleado.getEmpleado()).toString());
/* 133:162 */         break;
/* 134:    */       case 14: 
/* 135:165 */         FuncionesUtiles funciones = new FuncionesUtiles();
/* 136:166 */         respuesta = respuesta.replaceAll(variablesContrato.getVariable(), funciones
/* 137:167 */           .convertidorNumeroALetras(this.servicioRubroEmpleado.obtenerSueldoPorEmpleado(auxHistoricoEmpleado.getEmpleado()), true));
/* 138:    */       }
/* 139:    */     }
/* 140:176 */     return respuesta;
/* 141:    */   }
/* 142:    */   
/* 143:    */   protected String getCompileFileName()
/* 144:    */   {
/* 145:186 */     return "contrato_formato";
/* 146:    */   }
/* 147:    */   
/* 148:    */   public String execute()
/* 149:    */   {
/* 150:    */     try
/* 151:    */     {
/* 152:191 */       super.prepareReport();
/* 153:    */     }
/* 154:    */     catch (JRException e)
/* 155:    */     {
/* 156:193 */       LOG.info("Error JRException " + e);
/* 157:194 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 158:    */     }
/* 159:    */     catch (IOException e)
/* 160:    */     {
/* 161:196 */       LOG.info("Error IOException + " + e);
/* 162:197 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 163:    */     }
/* 164:199 */     return "";
/* 165:    */   }
/* 166:    */   
/* 167:    */   public HistoricoEmpleado getHistoricoEmpleado()
/* 168:    */   {
/* 169:208 */     if (this.historicoEmpleado == null) {
/* 170:209 */       this.historicoEmpleado = new HistoricoEmpleado();
/* 171:    */     }
/* 172:211 */     return this.historicoEmpleado;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setHistoricoEmpleado(HistoricoEmpleado historicoEmpleado)
/* 176:    */   {
/* 177:221 */     this.historicoEmpleado = historicoEmpleado;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public HistoricoEmpleado getAuxHistoricoEmpleado()
/* 181:    */   {
/* 182:225 */     return this.auxHistoricoEmpleado;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setAuxHistoricoEmpleado(HistoricoEmpleado auxHistoricoEmpleado)
/* 186:    */   {
/* 187:229 */     this.auxHistoricoEmpleado = auxHistoricoEmpleado;
/* 188:    */   }
/* 189:    */   
/* 190:    */   protected Map<String, Object> getReportParameters()
/* 191:    */   {
/* 192:234 */     this.auxHistoricoEmpleado = this.servicioHistoricoEmpleado.cargarDetalle(this.historicoEmpleado.getIdHistoricoEmpleado());
/* 193:235 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 194:236 */     reportParameters.put("ReportTitle", "CONTRATO DE TRABAJO " + this.auxHistoricoEmpleado.getEmpleado().getTipoContrato().getNombre());
/* 195:    */     
/* 196:238 */     return reportParameters;
/* 197:    */   }
/* 198:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.reportes.ReporteTipoContratoBean
 * JD-Core Version:    0.7.0.1
 */