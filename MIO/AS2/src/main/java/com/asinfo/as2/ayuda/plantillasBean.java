/*   1:    */ package com.asinfo.as2.ayuda;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Plantilla;
/*   6:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import javax.annotation.PostConstruct;
/*  11:    */ import javax.ejb.EJB;
/*  12:    */ import javax.faces.bean.ManagedBean;
/*  13:    */ import javax.faces.bean.ViewScoped;
/*  14:    */ import org.primefaces.component.datatable.DataTable;
/*  15:    */ import org.primefaces.model.LazyDataModel;
/*  16:    */ import org.primefaces.model.SortOrder;
/*  17:    */ 
/*  18:    */ @ManagedBean
/*  19:    */ @ViewScoped
/*  20:    */ public class plantillasBean
/*  21:    */   extends PageControllerAS2
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = -7213077706907154319L;
/*  24:    */   @EJB
/*  25:    */   private ServicioGenerico<Plantilla> servicioPlantillas;
/*  26:    */   private LazyDataModel<Plantilla> listaPlantillaLazy;
/*  27: 48 */   private String nombre = null;
/*  28: 49 */   private String ruta = null;
/*  29:    */   private DataTable dtPlantilla;
/*  30:    */   private Plantilla plantilla;
/*  31:    */   
/*  32:    */   @PostConstruct
/*  33:    */   public void init()
/*  34:    */   {
/*  35: 56 */     this.listaPlantillaLazy = new LazyDataModel()
/*  36:    */     {
/*  37:    */       private static final long serialVersionUID = 1L;
/*  38:    */       
/*  39:    */       public List<Plantilla> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  40:    */       {
/*  41: 63 */         List<Plantilla> lista = new ArrayList();
/*  42: 64 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  43:    */         
/*  44: 66 */         lista = plantillasBean.this.servicioPlantillas.obtenerListaPorPagina(Plantilla.class, startIndex, pageSize, sortField, ordenar, filters);
/*  45: 67 */         plantillasBean.this.listaPlantillaLazy.setRowCount(plantillasBean.this.servicioPlantillas.contarPorCriterio(Plantilla.class, filters));
/*  46:    */         
/*  47: 69 */         return lista;
/*  48:    */       }
/*  49:    */     };
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void exportarPlantilla()
/*  53:    */   {
/*  54: 76 */     if (this.plantilla != null)
/*  55:    */     {
/*  56: 77 */       setNombre(this.plantilla.getNombreProceso());
/*  57: 78 */       setRuta(this.plantilla.getRuta());
/*  58: 79 */       descargarPlantilla();
/*  59:    */     }
/*  60:    */     else
/*  61:    */     {
/*  62: 81 */       addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   public String getRutaPlantilla()
/*  67:    */   {
/*  68: 87 */     return this.ruta;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String getNombrePlantilla()
/*  72:    */   {
/*  73: 92 */     return this.nombre;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String getNombre()
/*  77:    */   {
/*  78: 96 */     return this.nombre;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setNombre(String nombre)
/*  82:    */   {
/*  83:100 */     this.nombre = nombre;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String getRuta()
/*  87:    */   {
/*  88:104 */     return this.ruta;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setRuta(String ruta)
/*  92:    */   {
/*  93:108 */     this.ruta = ruta;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public DataTable getDtPlantilla()
/*  97:    */   {
/*  98:112 */     return this.dtPlantilla;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setDtPlantilla(DataTable dtPlantilla)
/* 102:    */   {
/* 103:116 */     this.dtPlantilla = dtPlantilla;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public LazyDataModel<Plantilla> getListaPlantillaLazy()
/* 107:    */   {
/* 108:120 */     return this.listaPlantillaLazy;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setListaPlantillaLazy(LazyDataModel<Plantilla> listaPlantillaLazy)
/* 112:    */   {
/* 113:124 */     this.listaPlantillaLazy = listaPlantillaLazy;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public Plantilla getPlantilla()
/* 117:    */   {
/* 118:128 */     return this.plantilla;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setPlantilla(Plantilla plantilla)
/* 122:    */   {
/* 123:132 */     this.plantilla = plantilla;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public String editar()
/* 127:    */   {
/* 128:138 */     return null;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public String guardar()
/* 132:    */   {
/* 133:144 */     return null;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public String eliminar()
/* 137:    */   {
/* 138:150 */     return null;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public String limpiar()
/* 142:    */   {
/* 143:156 */     return null;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public String cargarDatos()
/* 147:    */   {
/* 148:162 */     return null;
/* 149:    */   }
/* 150:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ayuda.plantillasBean
 * JD-Core Version:    0.7.0.1
 */