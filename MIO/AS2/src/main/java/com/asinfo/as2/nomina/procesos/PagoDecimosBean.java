/*   1:    */ package com.asinfo.as2.nomina.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Quincena;
/*   6:    */ import com.asinfo.as2.enumeraciones.DecimosEnum;
/*   7:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioQuincena;
/*   8:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoDecimos;
/*   9:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  10:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  11:    */ import java.io.File;
/*  12:    */ import java.io.PrintStream;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.Date;
/*  15:    */ import java.util.HashMap;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Map;
/*  18:    */ import javax.ejb.EJB;
/*  19:    */ import javax.faces.bean.ManagedBean;
/*  20:    */ import javax.faces.bean.ViewScoped;
/*  21:    */ import javax.faces.model.SelectItem;
/*  22:    */ import org.apache.log4j.Logger;
/*  23:    */ import org.primefaces.model.StreamedContent;
/*  24:    */ 
/*  25:    */ @ManagedBean
/*  26:    */ @ViewScoped
/*  27:    */ public class PagoDecimosBean
/*  28:    */   extends PageControllerAS2
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = -5342767940488535266L;
/*  31:    */   private List<SelectItem> listaDecimos;
/*  32:    */   private List<Quincena> listaQuincena;
/*  33:    */   private DecimosEnum decimo;
/*  34:    */   private StreamedContent file;
/*  35:    */   private static final String TIPO_CONTENIDO = "application/txt";
/*  36:    */   private int anio;
/*  37:    */   @EJB
/*  38:    */   private ServicioPagoDecimos servicioPagoDecimos;
/*  39:    */   @EJB
/*  40:    */   private ServicioQuincena servicioQuincena;
/*  41:    */   
/*  42:    */   public String editar()
/*  43:    */   {
/*  44: 78 */     return null;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public String guardar()
/*  48:    */   {
/*  49: 89 */     return null;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public String eliminar()
/*  53:    */   {
/*  54:100 */     return null;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public String limpiar()
/*  58:    */   {
/*  59:111 */     return null;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public String cargarDatos()
/*  63:    */   {
/*  64:122 */     return null;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public StreamedContent generarArchivo()
/*  68:    */   {
/*  69:126 */     StreamedContent file = null;
/*  70:127 */     Map<String, String> filters = new HashMap();
/*  71:128 */     Date fechaDesde = null;
/*  72:129 */     Date fechaHasta = null;
/*  73:130 */     Quincena quincenaAux = null;
/*  74:    */     try
/*  75:    */     {
/*  76:134 */       List<Object[]> datosArchivo = null;
/*  77:135 */       String nombreArchivo = "";
/*  78:136 */       int tamanioArchivo = 0;
/*  79:137 */       switch (1.$SwitchMap$com$asinfo$as2$enumeraciones$DecimosEnum[this.decimo.ordinal()])
/*  80:    */       {
/*  81:    */       case 1: 
/*  82:141 */         quincenaAux = this.servicioQuincena.obtenerQuincenaPorCodigo("DT");
/*  83:142 */         System.out.println("Nombre quincena >>>>" + quincenaAux.getNombre());
/*  84:    */         
/*  85:144 */         fechaDesde = FuncionesUtiles.getFecha(1, 12, 2011);
/*  86:145 */         fechaHasta = FuncionesUtiles.getFecha(30, 11, 2012);
/*  87:    */         
/*  88:    */ 
/*  89:148 */         datosArchivo = this.servicioPagoDecimos.datosArchivoDecimoTercero(fechaDesde, fechaHasta);
/*  90:149 */         nombreArchivo = "decimoTercero.csv";
/*  91:150 */         tamanioArchivo = 10;
/*  92:151 */         break;
/*  93:    */       case 2: 
/*  94:155 */         quincenaAux = this.servicioQuincena.obtenerQuincenaPorCodigo("DC");
/*  95:156 */         System.out.println("Nombre quincena >>>>" + quincenaAux.getNombre());
/*  96:    */         
/*  97:158 */         fechaDesde = FuncionesUtiles.getFecha(1, 8, 2011);
/*  98:159 */         fechaHasta = FuncionesUtiles.getFecha(31, 7, 2012);
/*  99:    */         
/* 100:161 */         datosArchivo = this.servicioPagoDecimos.datosArchivoDecimoCuarto(fechaDesde, fechaHasta);
/* 101:162 */         nombreArchivo = "decimoCuarto.csv";
/* 102:163 */         tamanioArchivo = 11;
/* 103:164 */         break;
/* 104:    */       case 3: 
/* 105:167 */         datosArchivo = this.servicioPagoDecimos.datosArchivoUtilidades(getAnio(), "DT", "DC", "fonres");
/* 106:168 */         nombreArchivo = "utilidades.csv";
/* 107:169 */         tamanioArchivo = 18;
/* 108:170 */         break;
/* 109:    */       default: 
/* 110:173 */         datosArchivo = null;
/* 111:174 */         nombreArchivo = null;
/* 112:175 */         tamanioArchivo = 0;
/* 113:176 */         LOG.info("Seleccione una opcion ");
/* 114:    */       }
/* 115:180 */       if ((datosArchivo != null) && (nombreArchivo != null) && (tamanioArchivo > 0))
/* 116:    */       {
/* 117:181 */         String rutaArchivoCsv = ParametrosSistema.getAS2_HOME() + File.separator + "documentos" + File.separator + "pagoDecimo" + File.separator;
/* 118:    */         
/* 119:    */ 
/* 120:184 */         LOG.info("Ruta : " + rutaArchivoCsv);
/* 121:    */         
/* 122:186 */         FuncionesUtiles.crearArchivoTxt(rutaArchivoCsv, nombreArchivo, datosArchivo, tamanioArchivo, ",");
/* 123:    */         
/* 124:188 */         file = FuncionesUtiles.descargarArchivo(rutaArchivoCsv + nombreArchivo, "application/txt", nombreArchivo);
/* 125:    */       }
/* 126:    */     }
/* 127:    */     catch (Exception e)
/* 128:    */     {
/* 129:192 */       addErrorMessage(getLanguageController().getMensaje("msg_error_archivo_generado"));
/* 130:193 */       LOG.error("ERROR AL GENERAR EL ARCHIVO ", e);
/* 131:    */     }
/* 132:195 */     return file;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public List<SelectItem> getListaDecimos()
/* 136:    */   {
/* 137:203 */     if (this.listaDecimos == null)
/* 138:    */     {
/* 139:204 */       this.listaDecimos = new ArrayList();
/* 140:205 */       for (DecimosEnum decimos : DecimosEnum.values())
/* 141:    */       {
/* 142:206 */         SelectItem item = new SelectItem(decimos, decimos.getNombre());
/* 143:207 */         this.listaDecimos.add(item);
/* 144:    */       }
/* 145:    */     }
/* 146:210 */     return this.listaDecimos;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public DecimosEnum getDecimo()
/* 150:    */   {
/* 151:219 */     return this.decimo;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setDecimo(DecimosEnum decimo)
/* 155:    */   {
/* 156:229 */     this.decimo = decimo;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public StreamedContent getFile()
/* 160:    */   {
/* 161:238 */     this.file = generarArchivo();
/* 162:239 */     return this.file;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setFile(StreamedContent file)
/* 166:    */   {
/* 167:249 */     this.file = file;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public int getAnio()
/* 171:    */   {
/* 172:258 */     return this.anio;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setAnio(int anio)
/* 176:    */   {
/* 177:268 */     this.anio = anio;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public List<Quincena> getListaQuincena()
/* 181:    */   {
/* 182:277 */     return this.listaQuincena;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setListaQuincena(List<Quincena> listaQuincena)
/* 186:    */   {
/* 187:287 */     this.listaQuincena = listaQuincena;
/* 188:    */   }
/* 189:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.PagoDecimosBean
 * JD-Core Version:    0.7.0.1
 */