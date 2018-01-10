/*   1:    */ package com.asinfo.as2.nomina.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.CargoEmpleado;
/*   5:    */ import com.asinfo.as2.entities.Ciudad;
/*   6:    */ import com.asinfo.as2.entities.DireccionEmpresa;
/*   7:    */ import com.asinfo.as2.entities.Empleado;
/*   8:    */ import com.asinfo.as2.entities.Empresa;
/*   9:    */ import com.asinfo.as2.entities.LlamadoAtencion;
/*  10:    */ import com.asinfo.as2.entities.MotivoLlamadoAtencion;
/*  11:    */ import com.asinfo.as2.entities.Organizacion;
/*  12:    */ import com.asinfo.as2.entities.Sucursal;
/*  13:    */ import com.asinfo.as2.entities.TipoContrato;
/*  14:    */ import com.asinfo.as2.entities.Ubicacion;
/*  15:    */ import com.asinfo.as2.enumeraciones.VariablesMotivosLlamadosAtencion;
/*  16:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioLlamadoAtencion;
/*  17:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioRubroEmpleado;
/*  18:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  19:    */ import com.asinfo.as2.util.AppUtil;
/*  20:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  21:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  22:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  23:    */ import java.io.IOException;
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
/*  36:    */ public class ReporteMotivoLlamadoAtencionBean
/*  37:    */   extends AbstractBaseReportBean
/*  38:    */ {
/*  39:    */   private static final long serialVersionUID = -5571110678081092104L;
/*  40:    */   @EJB
/*  41:    */   private transient ServicioReporteNomina servicioReporteNomina;
/*  42:    */   @EJB
/*  43:    */   private transient ServicioLlamadoAtencion servicioLlamadoAtencion;
/*  44:    */   @EJB
/*  45:    */   private transient ServicioRubroEmpleado servicioRubroEmpleado;
/*  46:    */   @EJB
/*  47:    */   private transient ServicioGenerico<TipoContrato> servicioTipoContrato;
/*  48:    */   private LlamadoAtencion llamadoAtencion;
/*  49:    */   private LlamadoAtencion auxLlamadoAtencion;
/*  50:    */   
/*  51:    */   protected JRDataSource getJRDataSource()
/*  52:    */   {
/*  53: 71 */     JRDataSource jrDataSource = null;
/*  54:    */     try
/*  55:    */     {
/*  56: 73 */       String textoReporte = this.servicioReporteNomina.getReporteMotivoLlamadoAtencionPorId(this.llamadoAtencion.getMotivoLlamadoAtencion().getId());
/*  57: 74 */       String textoReemplazado = reemplazarTexto(textoReporte, this.auxLlamadoAtencion);
/*  58:    */       
/*  59: 76 */       List<Object[]> lista = new ArrayList();
/*  60: 77 */       Object[] objeto = new Object[1];
/*  61: 78 */       objeto[0] = textoReemplazado;
/*  62: 79 */       lista.add(objeto);
/*  63:    */       
/*  64: 81 */       String[] fields = { "f_mensaje" };
/*  65:    */       
/*  66: 83 */       jrDataSource = new QueryResultDataSource(lista, fields);
/*  67:    */     }
/*  68:    */     catch (Exception e)
/*  69:    */     {
/*  70: 86 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  71: 87 */       LOG.info("Error al cargar reporte de motivos de llamados de atencion " + e);
/*  72:    */     }
/*  73: 89 */     return jrDataSource;
/*  74:    */   }
/*  75:    */   
/*  76:    */   private String reemplazarTexto(String textoDato, LlamadoAtencion llamadoAtencion)
/*  77:    */   {
/*  78: 93 */     String respuesta = textoDato;
/*  79: 94 */     respuesta = respuesta.replaceAll("strong", "b");
/*  80: 95 */     for (VariablesMotivosLlamadosAtencion vmlat : VariablesMotivosLlamadosAtencion.values()) {
/*  81: 96 */       switch (1.$SwitchMap$com$asinfo$as2$enumeraciones$VariablesMotivosLlamadosAtencion[vmlat.ordinal()])
/*  82:    */       {
/*  83:    */       case 1: 
/*  84: 99 */         respuesta = respuesta.replaceAll(vmlat.getVariable(), FuncionesUtiles.convertidorFechaALetras(getLlamadoAtencion().getFechaDesde()));
/*  85:100 */         break;
/*  86:    */       case 2: 
/*  87:103 */         respuesta = respuesta.replaceAll(vmlat.getVariable(), AppUtil.getOrganizacion().getRazonSocial());
/*  88:104 */         break;
/*  89:    */       case 3: 
/*  90:107 */         respuesta = respuesta.replaceAll(vmlat.getVariable(), AppUtil.getOrganizacion().getRepresentanteLegal());
/*  91:108 */         break;
/*  92:    */       case 4: 
/*  93:111 */         respuesta = respuesta.replaceAll(vmlat.getVariable(), AppUtil.getSucursal().getUbicacion().getDireccionCompleta());
/*  94:112 */         break;
/*  95:    */       case 5: 
/*  96:115 */         respuesta = respuesta.replaceAll(vmlat.getVariable(), AppUtil.getOrganizacion().getIdentificacion());
/*  97:116 */         break;
/*  98:    */       case 6: 
/*  99:119 */         respuesta = respuesta.replaceAll(vmlat.getVariable(), AppUtil.getSucursal().getTelefono1());
/* 100:120 */         break;
/* 101:    */       case 7: 
/* 102:123 */         if (this.auxLlamadoAtencion.getEmpleado().getEmpresa().getDirecciones().isEmpty()) {
/* 103:124 */           respuesta = respuesta.replaceAll(vmlat.getVariable(), "");
/* 104:    */         } else {
/* 105:126 */           respuesta = respuesta.replaceAll(vmlat.getVariable(), 
/* 106:127 */             ((DireccionEmpresa)this.auxLlamadoAtencion.getEmpleado().getEmpresa().getDirecciones().get(0)).getCiudad().getNombre());
/* 107:    */         }
/* 108:129 */         break;
/* 109:    */       case 8: 
/* 110:132 */         respuesta = respuesta.replaceAll(vmlat.getVariable(), this.auxLlamadoAtencion
/* 111:133 */           .getEmpleado().getApellidos() + " " + this.auxLlamadoAtencion.getEmpleado().getNombres());
/* 112:134 */         break;
/* 113:    */       case 9: 
/* 114:137 */         respuesta = respuesta.replaceAll(vmlat.getVariable(), this.auxLlamadoAtencion.getEmpleado().getEmpresa().getIdentificacion());
/* 115:138 */         break;
/* 116:    */       case 10: 
/* 117:141 */         respuesta = respuesta.replaceAll(vmlat.getVariable(), this.auxLlamadoAtencion.getEmpleado().getCargoEmpleado().getNombre());
/* 118:142 */         break;
/* 119:    */       case 11: 
/* 120:145 */         if (this.auxLlamadoAtencion.getEmpleado().getEmpresa().getDirecciones().isEmpty()) {
/* 121:146 */           respuesta = respuesta.replaceAll(vmlat.getVariable(), "");
/* 122:    */         } else {
/* 123:148 */           respuesta = respuesta.replaceAll(vmlat.getVariable(), 
/* 124:149 */             ((DireccionEmpresa)this.auxLlamadoAtencion.getEmpleado().getEmpresa().getDirecciones().get(0)).getDireccionCompleta());
/* 125:    */         }
/* 126:    */         break;
/* 127:    */       }
/* 128:    */     }
/* 129:160 */     return respuesta;
/* 130:    */   }
/* 131:    */   
/* 132:    */   protected String getCompileFileName()
/* 133:    */   {
/* 134:170 */     return "reporteFormatoMotivoLlamadoAtencion";
/* 135:    */   }
/* 136:    */   
/* 137:    */   public String execute()
/* 138:    */   {
/* 139:    */     try
/* 140:    */     {
/* 141:175 */       super.prepareReport();
/* 142:    */     }
/* 143:    */     catch (JRException e)
/* 144:    */     {
/* 145:177 */       LOG.info("Error JRException " + e);
/* 146:178 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 147:    */     }
/* 148:    */     catch (IOException e)
/* 149:    */     {
/* 150:180 */       LOG.info("Error IOException + " + e);
/* 151:181 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 152:    */     }
/* 153:183 */     return "";
/* 154:    */   }
/* 155:    */   
/* 156:    */   public LlamadoAtencion getAuxLlamadoAtencion()
/* 157:    */   {
/* 158:187 */     return this.auxLlamadoAtencion;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setAuxLlamadoAtencion(LlamadoAtencion auxLlamadoAtencion)
/* 162:    */   {
/* 163:191 */     this.auxLlamadoAtencion = auxLlamadoAtencion;
/* 164:    */   }
/* 165:    */   
/* 166:    */   protected Map<String, Object> getReportParameters()
/* 167:    */   {
/* 168:196 */     this.auxLlamadoAtencion = this.servicioLlamadoAtencion.cargarDetalles(this.llamadoAtencion.getIdLlamadoAtencion());
/* 169:197 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 170:198 */     reportParameters.put("ReportTitle", "MOTIVO LLAMADO DE ATENCION " + this.auxLlamadoAtencion.getMotivoLlamadoAtencion().getNombre());
/* 171:    */     
/* 172:200 */     return reportParameters;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public LlamadoAtencion getLlamadoAtencion()
/* 176:    */   {
/* 177:204 */     if (this.llamadoAtencion == null) {
/* 178:205 */       this.llamadoAtencion = new LlamadoAtencion();
/* 179:    */     }
/* 180:207 */     return this.llamadoAtencion;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setLlamadoAtencion(LlamadoAtencion llamadoAtencion)
/* 184:    */   {
/* 185:211 */     this.llamadoAtencion = llamadoAtencion;
/* 186:    */   }
/* 187:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.reportes.ReporteMotivoLlamadoAtencionBean
 * JD-Core Version:    0.7.0.1
 */