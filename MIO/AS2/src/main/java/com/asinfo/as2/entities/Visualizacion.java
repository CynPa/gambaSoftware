/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.List;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.FetchType;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.OneToMany;
/*  13:    */ import javax.persistence.Table;
/*  14:    */ import javax.persistence.TableGenerator;
/*  15:    */ import javax.validation.constraints.NotNull;
/*  16:    */ import javax.validation.constraints.Size;
/*  17:    */ 
/*  18:    */ @Entity
/*  19:    */ @Table(name="visualizacion", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  20:    */ public class Visualizacion
/*  21:    */   extends EntidadBase
/*  22:    */   implements Serializable
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 1L;
/*  25:    */   @Id
/*  26:    */   @TableGenerator(name="visualizacion", initialValue=0, allocationSize=50)
/*  27:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="visualizacion")
/*  28:    */   @Column(name="id_visualizacion", unique=true, nullable=false)
/*  29:    */   private int idVisualizacion;
/*  30:    */   @Column(name="id_organizacion", nullable=false)
/*  31:    */   private int idOrganizacion;
/*  32:    */   @Column(name="id_sucursal", nullable=false)
/*  33:    */   private int idSucursal;
/*  34:    */   @Column(name="codigo", nullable=false, length=10)
/*  35:    */   @NotNull
/*  36:    */   @Size(min=1, max=10)
/*  37:    */   private String codigo;
/*  38:    */   @Column(name="nombre", nullable=false, length=50)
/*  39:    */   @NotNull
/*  40:    */   @Size(min=1, max=50)
/*  41:    */   private String nombre;
/*  42:    */   @Column(name="descripcion", nullable=true, length=300)
/*  43:    */   @Size(max=300)
/*  44:    */   private String descripcion;
/*  45:    */   @Column(name="activo", nullable=false)
/*  46:    */   @NotNull
/*  47: 67 */   private boolean activo = true;
/*  48:    */   @Column(name="predeterminado", nullable=false)
/*  49:    */   private boolean predeterminado;
/*  50:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="visualizacion")
/*  51: 74 */   private List<DetalleVisualizacion> listaDetalleVisualizacion = new ArrayList();
/*  52:    */   
/*  53:    */   public int getId()
/*  54:    */   {
/*  55: 82 */     return this.idVisualizacion;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int getIdVisualizacion()
/*  59:    */   {
/*  60: 86 */     return this.idVisualizacion;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setIdVisualizacion(int idVisualizacion)
/*  64:    */   {
/*  65: 90 */     this.idVisualizacion = idVisualizacion;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int getIdOrganizacion()
/*  69:    */   {
/*  70: 94 */     return this.idOrganizacion;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setIdOrganizacion(int idOrganizacion)
/*  74:    */   {
/*  75: 98 */     this.idOrganizacion = idOrganizacion;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int getIdSucursal()
/*  79:    */   {
/*  80:102 */     return this.idSucursal;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setIdSucursal(int idSucursal)
/*  84:    */   {
/*  85:106 */     this.idSucursal = idSucursal;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String getCodigo()
/*  89:    */   {
/*  90:110 */     return this.codigo;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setCodigo(String codigo)
/*  94:    */   {
/*  95:114 */     this.codigo = codigo;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String getNombre()
/*  99:    */   {
/* 100:118 */     return this.nombre;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setNombre(String nombre)
/* 104:    */   {
/* 105:122 */     this.nombre = nombre;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String getDescripcion()
/* 109:    */   {
/* 110:126 */     return this.descripcion;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setDescripcion(String descripcion)
/* 114:    */   {
/* 115:130 */     this.descripcion = descripcion;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public boolean isActivo()
/* 119:    */   {
/* 120:134 */     return this.activo;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setActivo(boolean activo)
/* 124:    */   {
/* 125:138 */     this.activo = activo;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public boolean isPredeterminado()
/* 129:    */   {
/* 130:142 */     return this.predeterminado;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setPredeterminado(boolean predeterminado)
/* 134:    */   {
/* 135:146 */     this.predeterminado = predeterminado;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public List<DetalleVisualizacion> getListaDetalleVisualizacion()
/* 139:    */   {
/* 140:150 */     return this.listaDetalleVisualizacion;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setListaDetalleVisualizacion(List<DetalleVisualizacion> listaDetalleVisualizacion)
/* 144:    */   {
/* 145:154 */     this.listaDetalleVisualizacion = listaDetalleVisualizacion;
/* 146:    */   }
/* 147:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Visualizacion
 * JD-Core Version:    0.7.0.1
 */