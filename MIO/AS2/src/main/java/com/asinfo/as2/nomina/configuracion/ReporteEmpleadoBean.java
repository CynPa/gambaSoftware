/*   1:    */ package com.asinfo.as2.nomina.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   5:    */ import com.asinfo.as2.entities.Ciudad;
/*   6:    */ import com.asinfo.as2.entities.Contacto;
/*   7:    */ import com.asinfo.as2.entities.DireccionEmpresa;
/*   8:    */ import com.asinfo.as2.entities.Empleado;
/*   9:    */ import com.asinfo.as2.entities.Empresa;
/*  10:    */ import com.asinfo.as2.entities.FormacionAcademica;
/*  11:    */ import com.asinfo.as2.entities.Organizacion;
/*  12:    */ import com.asinfo.as2.entities.Pais;
/*  13:    */ import com.asinfo.as2.entities.Provincia;
/*  14:    */ import com.asinfo.as2.entities.Ubicacion;
/*  15:    */ import com.asinfo.as2.enumeraciones.Genero;
/*  16:    */ import com.asinfo.as2.enumeraciones.TipoSangre;
/*  17:    */ import com.asinfo.as2.util.AppUtil;
/*  18:    */ import com.asinfo.as2.util.RutaArchivo;
/*  19:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  20:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  21:    */ import java.io.IOException;
/*  22:    */ import java.util.ArrayList;
/*  23:    */ import java.util.List;
/*  24:    */ import java.util.Map;
/*  25:    */ import javax.ejb.EJB;
/*  26:    */ import javax.faces.bean.ManagedBean;
/*  27:    */ import javax.faces.bean.ViewScoped;
/*  28:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  29:    */ import net.sf.jasperreports.engine.JRException;
/*  30:    */ import org.apache.log4j.Logger;
/*  31:    */ 
/*  32:    */ @ManagedBean
/*  33:    */ @ViewScoped
/*  34:    */ public class ReporteEmpleadoBean
/*  35:    */   extends AbstractBaseReportBean
/*  36:    */ {
/*  37:    */   @EJB
/*  38:    */   private ServicioEmpresa servicioEmpresa;
/*  39:    */   private static final long serialVersionUID = 770222109067650882L;
/*  40: 38 */   private final String COMPILE_FILE_NAME = "reporteFichaEmpleado";
/*  41:    */   private Empresa empresa;
/*  42:    */   
/*  43:    */   protected JRDataSource getJRDataSource()
/*  44:    */   {
/*  45: 48 */     List listaDatosReporte = new ArrayList();
/*  46: 49 */     JRDataSource ds = null;
/*  47:    */     try
/*  48:    */     {
/*  49: 51 */       listaDatosReporte = this.servicioEmpresa.getFichaEmpleado(this.empresa.getEmpleado().getIdEmpleado());
/*  50:    */       
/*  51: 53 */       String[] fields = { "f_nombresEmpleado", "f_apellidosEmpleado", "f_codigoEmpleado", "f_identificacionEmpleado", "f_sucursalEmpleado", "f_fotoEmpleado", "f_estadoCivilEmpleado", "f_tituloEmpleado", "f_tipoContratoEmpleado", "f_cargoEmpleado", "f_departamentoEmpleado", "f_emailEmpleado", "f_webEmpleado", "f_lugarNacimiento", "f_fechaNacimiento", "f_categoriaEmpleado", "f_nombreHorario", "f_indicadorRotativo", "f_horaEntradat0", "f_horaSalidat0", "f_horaEntradat1", "f_horaSalidat1", "f_horaEntradat2", "f_horaSalidat2", "f_horaEntradat3", "f_horaSalidat3", "f_horaEntradat4", "f_horaSalidat4", "f_horaEntradat5", "f_horaSalidat5", "f_horaEntradat6", "f_horaSalidat6", "f_fotoHuella1", "f_fotoHuella2", "f_fotoHuella3", "f_fotoHuella4", "f_fotoHuella5", "f_fotoHuella6", "f_fotoHuella7", "f_fotoHuella8", "f_fotoHuella9", "f_fotoHuella10" };
/*  52:    */       
/*  53:    */ 
/*  54:    */ 
/*  55:    */ 
/*  56:    */ 
/*  57: 59 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  58:    */     }
/*  59:    */     catch (Exception e)
/*  60:    */     {
/*  61: 61 */       e.printStackTrace();
/*  62:    */     }
/*  63: 63 */     return ds;
/*  64:    */   }
/*  65:    */   
/*  66:    */   protected String getCompileFileName()
/*  67:    */   {
/*  68: 68 */     return "reporteFichaEmpleado";
/*  69:    */   }
/*  70:    */   
/*  71:    */   protected Map<String, Object> getReportParameters()
/*  72:    */   {
/*  73: 73 */     this.empresa = this.servicioEmpresa.cargarDetalle(getEmpresa());
/*  74: 74 */     String uploadDir = RutaArchivo.getUploadDir(AppUtil.getOrganizacion().getId(), "empleado");
/*  75: 75 */     String huellasDir = RutaArchivo.getUploadDir(AppUtil.getOrganizacion().getId(), "huellas_digitales");
/*  76: 76 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  77: 77 */     reportParameters.put("ReportTitle", "Ficha del Empleado");
/*  78: 78 */     reportParameters.put("uploadDir", uploadDir);
/*  79: 79 */     reportParameters.put("huellasDir", huellasDir);
/*  80: 80 */     reportParameters.put("p_sexoEmpleado", this.empresa.getEmpleado().getGenero().name());
/*  81: 81 */     reportParameters.put("p_tipoSangre", this.empresa.getEmpleado().getTipoSangre() != null ? this.empresa.getEmpleado().getTipoSangre().getNombre() : "--");
/*  82: 82 */     reportParameters.put("p_estadoEmpleado", this.empresa.getEmpleado().isActivo() ? "Activo" : "Inactivo");
/*  83: 83 */     if (getDirecciones().size() > 0)
/*  84:    */     {
/*  85: 84 */       reportParameters.put("p_ubicacionResidencia", ((DireccionEmpresa)getDirecciones().get(0)).getCiudad().getProvincia().getPais().getNombre() + "/" + ((DireccionEmpresa)getDirecciones().get(0)).getCiudad().getProvincia().getNombre() + "/" + ((DireccionEmpresa)getDirecciones().get(0)).getCiudad().getNombre());
/*  86: 85 */       reportParameters.put("p_callePrincipalResidencia", ((DireccionEmpresa)getDirecciones().get(0)).getUbicacion().getDireccion1());
/*  87: 86 */       reportParameters.put("p_numeroResidencia", ((DireccionEmpresa)getDirecciones().get(0)).getUbicacion().getDireccion2());
/*  88: 87 */       reportParameters.put("p_calleSecundariaResidencia", ((DireccionEmpresa)getDirecciones().get(0)).getUbicacion().getDireccion3());
/*  89: 88 */       reportParameters.put("p_barrio", ((DireccionEmpresa)getDirecciones().get(0)).getUbicacion().getDireccion4());
/*  90: 89 */       reportParameters.put("p_referencia", ((DireccionEmpresa)getDirecciones().get(0)).getUbicacion().getDireccion5());
/*  91: 90 */       reportParameters.put("p_telefonoResidencia", ((DireccionEmpresa)getDirecciones().get(0)).getTelefono1());
/*  92:    */     }
/*  93: 93 */     if (this.empresa.getListaContacto().size() > 0)
/*  94:    */     {
/*  95: 94 */       reportParameters.put("p_nombreContacto", ((Contacto)this.empresa.getListaContacto().get(0)).getNombre());
/*  96: 95 */       reportParameters.put("p_telefonoContacto", ((Contacto)this.empresa.getListaContacto().get(0)).getTelefono1());
/*  97: 96 */       reportParameters.put("p_emailContacto", ((Contacto)this.empresa.getListaContacto().get(0)).getEmail1());
/*  98:    */     }
/*  99: 98 */     reportParameters.put("SUBREPORT_DIR", getPathReportes());
/* 100:    */     
/* 101:100 */     List<FormacionAcademica> datosFormacionAcademica = this.servicioEmpresa.getFichaEmpleadoFormacionAcademica(this.empresa.getEmpleado()
/* 102:101 */       .getIdEmpleado());
/* 103:102 */     reportParameters.put("p_datosFormacionAcademica", datosFormacionAcademica);
/* 104:    */     
/* 105:104 */     return reportParameters;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String execute()
/* 109:    */   {
/* 110:    */     try
/* 111:    */     {
/* 112:113 */       super.prepareReport();
/* 113:    */     }
/* 114:    */     catch (JRException e)
/* 115:    */     {
/* 116:116 */       LOG.info("Error JRException");
/* 117:117 */       e.printStackTrace();
/* 118:118 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 119:    */     }
/* 120:    */     catch (IOException e)
/* 121:    */     {
/* 122:120 */       LOG.info("Error IOException");
/* 123:121 */       e.printStackTrace();
/* 124:122 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 125:    */     }
/* 126:125 */     return "";
/* 127:    */   }
/* 128:    */   
/* 129:    */   public Empresa getEmpresa()
/* 130:    */   {
/* 131:134 */     if (this.empresa == null) {
/* 132:135 */       this.empresa = new Empresa();
/* 133:    */     }
/* 134:137 */     return this.empresa;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setEmpresa(Empresa empresa)
/* 138:    */   {
/* 139:147 */     this.empresa = empresa;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public List<DireccionEmpresa> getDirecciones()
/* 143:    */   {
/* 144:152 */     List<DireccionEmpresa> direcciones = new ArrayList();
/* 145:153 */     for (DireccionEmpresa de : getEmpresa().getDirecciones()) {
/* 146:155 */       if (!de.isEliminado()) {
/* 147:156 */         direcciones.add(de);
/* 148:    */       }
/* 149:    */     }
/* 150:160 */     return direcciones;
/* 151:    */   }
/* 152:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.ReporteEmpleadoBean
 * JD-Core Version:    0.7.0.1
 */