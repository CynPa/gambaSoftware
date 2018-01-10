/*   1:    */ package com.asinfo.as2.datosbase.controller.migracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.migracion.ServicioMigracion;
/*   6:    */ import com.asinfo.as2.entities.Atributo;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*   9:    */ import com.asinfo.as2.entities.Sucursal;
/*  10:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  11:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  12:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  13:    */ import com.asinfo.as2.util.AppUtil;
/*  14:    */ import com.asinfo.as2.util.RutaArchivo;
/*  15:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  16:    */ import com.asinfo.as2.utils.JsfUtil;
/*  17:    */ import java.io.BufferedInputStream;
/*  18:    */ import java.io.InputStream;
/*  19:    */ import java.util.ArrayList;
/*  20:    */ import java.util.Date;
/*  21:    */ import java.util.List;
/*  22:    */ import javax.ejb.EJB;
/*  23:    */ import javax.faces.bean.ManagedBean;
/*  24:    */ import javax.faces.bean.ViewScoped;
/*  25:    */ import org.apache.log4j.Logger;
/*  26:    */ import org.primefaces.event.FileUploadEvent;
/*  27:    */ import org.primefaces.model.StreamedContent;
/*  28:    */ import org.primefaces.model.UploadedFile;
/*  29:    */ 
/*  30:    */ @ManagedBean
/*  31:    */ @ViewScoped
/*  32:    */ public class MigracionLoteBean
/*  33:    */   extends PageControllerAS2
/*  34:    */ {
/*  35:    */   private static final long serialVersionUID = -7734245936478862749L;
/*  36:    */   @EJB
/*  37:    */   private ServicioMigracion servicioMigracion;
/*  38:    */   @EJB
/*  39:    */   private ServicioGenerico<OrganizacionConfiguracion> servicioOrganizacionConfiguracion;
/*  40:    */   private OrganizacionConfiguracion organizacionConfiguracion;
/*  41:    */   private StreamedContent file;
/*  42:    */   
/*  43:    */   public String editar()
/*  44:    */   {
/*  45: 53 */     return null;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public String guardar()
/*  49:    */   {
/*  50: 59 */     return null;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public String eliminar()
/*  54:    */   {
/*  55: 65 */     return null;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String limpiar()
/*  59:    */   {
/*  60: 71 */     return null;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String cargarDatos()
/*  64:    */   {
/*  65: 77 */     return null;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String migrarLotes(FileUploadEvent event)
/*  69:    */   {
/*  70:    */     try
/*  71:    */     {
/*  72: 83 */       String fileName = "migracion_lote" + event.getFile().getFileName();
/*  73: 84 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/*  74: 85 */       this.servicioMigracion.migracionLote(fileName, input, 3, AppUtil.getOrganizacion().getId(), AppUtil.getSucursal().getId(), 
/*  75: 86 */         getOrganizacionConfiguracion());
/*  76: 87 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/*  77:    */     }
/*  78:    */     catch (ExcepcionAS2 e)
/*  79:    */     {
/*  80: 90 */       e.printStackTrace();
/*  81: 91 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  82:    */     }
/*  83:    */     catch (AS2Exception e)
/*  84:    */     {
/*  85: 93 */       e.printStackTrace();
/*  86: 94 */       JsfUtil.addErrorMessage(e, "");
/*  87:    */     }
/*  88:    */     catch (Exception e)
/*  89:    */     {
/*  90: 96 */       e.printStackTrace();
/*  91: 97 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  92:    */     }
/*  93: 99 */     return null;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String getRutaPlantilla()
/*  97:    */   {
/*  98:105 */     return "/resources/plantillas/inventario/AS2 Lote.xls";
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String getNombrePlantilla()
/* 102:    */   {
/* 103:110 */     return "AS2 Lote.xls";
/* 104:    */   }
/* 105:    */   
/* 106:    */   public StreamedContent getFile()
/* 107:    */   {
/* 108:114 */     this.file = generarPlantilla();
/* 109:115 */     return this.file;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public StreamedContent generarPlantilla()
/* 113:    */   {
/* 114:    */     try
/* 115:    */     {
/* 116:123 */       String rutaArchivo = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "plantillas", "migracionLote");
/* 117:124 */       String nombreArchivo = "migracionLote.xls";
/* 118:125 */       int numeroColumnas = 5 + AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNumeroAtributos();
/* 119:    */       
/* 120:    */ 
/* 121:128 */       List<Object[]> listaDatos = new ArrayList();
/* 122:129 */       Object[] datos = new Object[numeroColumnas];
/* 123:130 */       datos[0] = Integer.valueOf(0);
/* 124:131 */       datos[1] = Integer.valueOf(1);
/* 125:132 */       datos[2] = Integer.valueOf(2);
/* 126:133 */       datos[3] = Integer.valueOf(3);
/* 127:134 */       datos[4] = Integer.valueOf(4);
/* 128:135 */       for (int i = 5; i < numeroColumnas; i++) {
/* 129:136 */         datos[i] = Integer.valueOf(i);
/* 130:    */       }
/* 131:138 */       listaDatos.add(datos);
/* 132:    */       
/* 133:    */ 
/* 134:141 */       datos = new Object[numeroColumnas];
/* 135:142 */       datos[0] = "Codigo Lote";
/* 136:143 */       datos[1] = "Codigo Producto";
/* 137:144 */       datos[2] = "Momiento Interno";
/* 138:145 */       datos[3] = "Fecha Facturacion";
/* 139:146 */       datos[4] = "Fecha Caducidad";
/* 140:148 */       if (getOrganizacionConfiguracion().getAtributo1() != null) {
/* 141:149 */         datos[5] = ("Codigo " + getOrganizacionConfiguracion().getAtributo1().getNombre());
/* 142:    */       }
/* 143:151 */       if (getOrganizacionConfiguracion().getAtributo2() != null) {
/* 144:152 */         datos[6] = ("Codigo " + getOrganizacionConfiguracion().getAtributo2().getNombre());
/* 145:    */       }
/* 146:154 */       if (getOrganizacionConfiguracion().getAtributo3() != null) {
/* 147:155 */         datos[7] = ("Codigo " + getOrganizacionConfiguracion().getAtributo3().getNombre());
/* 148:    */       }
/* 149:157 */       if (getOrganizacionConfiguracion().getAtributo4() != null) {
/* 150:158 */         datos[8] = ("Codigo " + getOrganizacionConfiguracion().getAtributo4().getNombre());
/* 151:    */       }
/* 152:160 */       if (getOrganizacionConfiguracion().getAtributo5() != null) {
/* 153:161 */         datos[9] = ("Codigo " + getOrganizacionConfiguracion().getAtributo5().getNombre());
/* 154:    */       }
/* 155:163 */       if (getOrganizacionConfiguracion().getAtributo6() != null) {
/* 156:164 */         datos[10] = ("Codigo " + getOrganizacionConfiguracion().getAtributo6().getNombre());
/* 157:    */       }
/* 158:166 */       if (getOrganizacionConfiguracion().getAtributo7() != null) {
/* 159:167 */         datos[11] = ("Codigo " + getOrganizacionConfiguracion().getAtributo7().getNombre());
/* 160:    */       }
/* 161:169 */       if (getOrganizacionConfiguracion().getAtributo8() != null) {
/* 162:170 */         datos[12] = ("Codigo " + getOrganizacionConfiguracion().getAtributo8().getNombre());
/* 163:    */       }
/* 164:172 */       if (getOrganizacionConfiguracion().getAtributo9() != null) {
/* 165:173 */         datos[13] = ("Codigo " + getOrganizacionConfiguracion().getAtributo9().getNombre());
/* 166:    */       }
/* 167:175 */       if (getOrganizacionConfiguracion().getAtributo10() != null) {
/* 168:176 */         datos[14] = ("Codigo " + getOrganizacionConfiguracion().getAtributo10().getNombre());
/* 169:    */       }
/* 170:178 */       listaDatos.add(datos);
/* 171:181 */       for (int i = 1; i <= 10; i++)
/* 172:    */       {
/* 173:182 */         datos = new Object[numeroColumnas];
/* 174:183 */         datos[0] = FuncionesUtiles.completarALaIzquierda('0', 10, "" + i);
/* 175:184 */         datos[1] = FuncionesUtiles.completarALaIzquierda('0', 10, "" + i);
/* 176:185 */         datos[2] = "SI";
/* 177:186 */         datos[3] = new Date();
/* 178:187 */         datos[4] = new Date();
/* 179:188 */         for (int j = 5; j < numeroColumnas; j++) {
/* 180:189 */           datos[j] = "";
/* 181:    */         }
/* 182:191 */         listaDatos.add(datos);
/* 183:    */       }
/* 184:195 */       FuncionesUtiles.crearExcel(rutaArchivo + nombreArchivo, listaDatos, numeroColumnas, true);
/* 185:    */       
/* 186:    */ 
/* 187:198 */       this.file = FuncionesUtiles.descargarArchivo(rutaArchivo + nombreArchivo, "application/xls", nombreArchivo);
/* 188:    */       
/* 189:200 */       addInfoMessage(getLanguageController().getMensaje("msg_info_archivo_generado"));
/* 190:    */     }
/* 191:    */     catch (Exception e)
/* 192:    */     {
/* 193:203 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 194:204 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 195:    */     }
/* 196:207 */     return this.file;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public OrganizacionConfiguracion getOrganizacionConfiguracion()
/* 200:    */   {
/* 201:212 */     if (this.organizacionConfiguracion == null)
/* 202:    */     {
/* 203:213 */       int numeroAtributos = AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNumeroAtributos();
/* 204:214 */       List<String> listaCampos = new ArrayList();
/* 205:215 */       for (int i = 1; i <= numeroAtributos; i++) {
/* 206:216 */         listaCampos.add("atributo" + i);
/* 207:    */       }
/* 208:218 */       this.organizacionConfiguracion = ((OrganizacionConfiguracion)this.servicioOrganizacionConfiguracion.cargarDetalle(OrganizacionConfiguracion.class, 
/* 209:219 */         AppUtil.getOrganizacion().getOrganizacionConfiguracion().getIdOrganizacionConfiguracion(), listaCampos));
/* 210:    */     }
/* 211:221 */     return this.organizacionConfiguracion;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void setOrganizacionConfiguracion(OrganizacionConfiguracion organizacionConfiguracion)
/* 215:    */   {
/* 216:225 */     this.organizacionConfiguracion = organizacionConfiguracion;
/* 217:    */   }
/* 218:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.controller.migracion.controller.MigracionLoteBean
 * JD-Core Version:    0.7.0.1
 */