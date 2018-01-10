/*   1:    */ package com.asinfo.as2.utils;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.CodigoFormaPagoSRI;
/*   4:    */ import java.io.File;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.HashMap;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import javax.faces.model.SelectItem;
/*  12:    */ import org.jdom2.Document;
/*  13:    */ import org.jdom2.Element;
/*  14:    */ import org.jdom2.JDOMException;
/*  15:    */ import org.jdom2.input.SAXBuilder;
/*  16:    */ 
/*  17:    */ public class DatosSRI
/*  18:    */   implements Serializable
/*  19:    */ {
/*  20:    */   private static final long serialVersionUID = -5227735737417544943L;
/*  21:    */   private static final String CARPETA_CATALOGO_SRI = "catalogoSRI";
/*  22: 43 */   private static List<SelectItem> listaDistritoAduanero = new ArrayList();
/*  23: 44 */   private static List<SelectItem> listaRegimen = new ArrayList();
/*  24: 46 */   private static Map<Integer, List<SelectItem>> hashFormaPagoSRI = new HashMap();
/*  25: 49 */   private static List<SelectItem> listaPaises = new ArrayList();
/*  26: 51 */   private static List<SelectItem> listaTipoIngresoExterior = new ArrayList();
/*  27:    */   
/*  28:    */   public static void cargarDistritoAduanero(String nombreArchivo, int idOrganizacion)
/*  29:    */     throws JDOMException, IOException
/*  30:    */   {
/*  31: 60 */     SAXBuilder builder = new SAXBuilder();
/*  32: 61 */     File file = new File(obtenerDirectorioATS(idOrganizacion) + File.separator + nombreArchivo);
/*  33:    */     
/*  34: 63 */     Document documento = builder.build(file);
/*  35: 64 */     Element nodoPrincipal = documento.getRootElement();
/*  36:    */     
/*  37: 66 */     List<Element> listaDistritoAduaneroElemento = nodoPrincipal.getChildren("distritoAduanero");
/*  38: 68 */     for (Element elemento : listaDistritoAduaneroElemento)
/*  39:    */     {
/*  40: 69 */       SelectItem item = new SelectItem(elemento.getAttributeValue("codigo"), elemento.getAttributeValue("nombre"));
/*  41: 70 */       listaDistritoAduanero.add(item);
/*  42:    */     }
/*  43:    */   }
/*  44:    */   
/*  45:    */   public static void cargarTipoIngresoExterior(String nombreArchivo, int idOrganizacion)
/*  46:    */     throws JDOMException, IOException
/*  47:    */   {
/*  48: 83 */     SAXBuilder builder = new SAXBuilder();
/*  49: 84 */     File file = new File(obtenerDirectorioATS(idOrganizacion) + File.separator + nombreArchivo);
/*  50:    */     
/*  51: 86 */     Document documento = builder.build(file);
/*  52: 87 */     Element nodoPrincipal = documento.getRootElement();
/*  53:    */     
/*  54: 89 */     List<Element> listaDistritoAduaneroElemento = nodoPrincipal.getChildren("tipoIngresoExterior");
/*  55: 91 */     for (Element elemento : listaDistritoAduaneroElemento)
/*  56:    */     {
/*  57: 92 */       SelectItem item = new SelectItem(elemento.getAttributeValue("codigo"), elemento.getAttributeValue("nombre"));
/*  58: 93 */       listaTipoIngresoExterior.add(item);
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static void cargarRegimen(String nombreArchivo, int idOrganizacion)
/*  63:    */     throws JDOMException, IOException
/*  64:    */   {
/*  65:105 */     SAXBuilder builder = new SAXBuilder();
/*  66:106 */     File file = new File(obtenerDirectorioATS(idOrganizacion) + File.separator + nombreArchivo);
/*  67:    */     
/*  68:108 */     Document documento = builder.build(file);
/*  69:109 */     Element nodoPrincipal = documento.getRootElement();
/*  70:    */     
/*  71:111 */     List<Element> listaRegimenElemento = nodoPrincipal.getChildren("regimen");
/*  72:113 */     for (Element elemento : listaRegimenElemento)
/*  73:    */     {
/*  74:114 */       SelectItem item = new SelectItem(elemento.getAttributeValue("codigo"), elemento.getAttributeValue("nombre"));
/*  75:115 */       listaRegimen.add(item);
/*  76:    */     }
/*  77:    */   }
/*  78:    */   
/*  79:    */   public static void cargarFormaPago(int idOrganizacion, List<CodigoFormaPagoSRI> listCodigoFormaPagoSRIDB)
/*  80:    */   {
/*  81:121 */     List<SelectItem> listaCodigoFormaPagoSRI = new ArrayList();
/*  82:122 */     for (CodigoFormaPagoSRI elemento : listCodigoFormaPagoSRIDB)
/*  83:    */     {
/*  84:123 */       SelectItem item = new SelectItem(elemento.getCodigo(), elemento.getNombre(), String.valueOf(elemento.isPredeterminado()));
/*  85:124 */       listaCodigoFormaPagoSRI.add(item);
/*  86:    */     }
/*  87:126 */     hashFormaPagoSRI.put(Integer.valueOf(idOrganizacion), listaCodigoFormaPagoSRI);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public static void cargarPaises(String nombreArchivo, int idOrganizacion)
/*  91:    */     throws JDOMException, IOException
/*  92:    */   {
/*  93:138 */     SAXBuilder builder = new SAXBuilder();
/*  94:139 */     File file = new File(obtenerDirectorioATS(idOrganizacion) + File.separator + nombreArchivo);
/*  95:    */     
/*  96:141 */     Document documento = builder.build(file);
/*  97:142 */     Element nodoPrincipal = documento.getRootElement();
/*  98:    */     
/*  99:144 */     List<Element> listaPaisElemento = nodoPrincipal.getChildren("pais");
/* 100:146 */     for (Element elemento : listaPaisElemento)
/* 101:    */     {
/* 102:147 */       SelectItem item = new SelectItem(elemento.getAttributeValue("codigo"), elemento.getAttributeValue("nombre"));
/* 103:148 */       listaPaises.add(item);
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   public static String obtenerDirectorioATS(int idOrganizacion)
/* 108:    */   {
/* 109:158 */     String directorioSRI = ParametrosSistema.getAS2_HOME(idOrganizacion) + File.separator + "catalogoSRI";
/* 110:159 */     File directorioArchivo = new File(directorioSRI);
/* 111:160 */     if (!directorioArchivo.exists()) {
/* 112:161 */       directorioArchivo.mkdirs();
/* 113:    */     }
/* 114:164 */     return directorioSRI;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public static List<SelectItem> getListaDistritoAduanero()
/* 118:    */   {
/* 119:173 */     return listaDistritoAduanero;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public static List<SelectItem> getListaRegimen()
/* 123:    */   {
/* 124:182 */     return listaRegimen;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public static List<SelectItem> getListaFormaPago(int idOrganizacion)
/* 128:    */   {
/* 129:186 */     List<SelectItem> listaCodigoFormaPagoSRI = (List)hashFormaPagoSRI.get(Integer.valueOf(idOrganizacion));
/* 130:187 */     if (listaCodigoFormaPagoSRI == null) {
/* 131:188 */       listaCodigoFormaPagoSRI = new ArrayList();
/* 132:    */     }
/* 133:190 */     return listaCodigoFormaPagoSRI;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public static List<SelectItem> getListaPaises()
/* 137:    */   {
/* 138:199 */     return listaPaises;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public static List<SelectItem> getListaTipoIngresoExterior()
/* 142:    */   {
/* 143:203 */     return listaTipoIngresoExterior;
/* 144:    */   }
/* 145:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.utils.DatosSRI
 * JD-Core Version:    0.7.0.1
 */