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
/*  12:    */ import javax.persistence.JoinTable;
/*  13:    */ import javax.persistence.ManyToMany;
/*  14:    */ import javax.persistence.Table;
/*  15:    */ import javax.persistence.TableGenerator;
/*  16:    */ import javax.validation.constraints.NotNull;
/*  17:    */ import javax.validation.constraints.Size;
/*  18:    */ 
/*  19:    */ @Entity
/*  20:    */ @Table(name="categoria_impuesto", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  21:    */ public class CategoriaImpuesto
/*  22:    */   extends EntidadBase
/*  23:    */   implements Serializable
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = 8366598238399592897L;
/*  26:    */   @Id
/*  27:    */   @TableGenerator(name="categoria_impuesto", initialValue=0, allocationSize=50)
/*  28:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="categoria_impuesto")
/*  29:    */   @Column(name="id_categoria_impuesto")
/*  30:    */   private int idCategoriaImpuesto;
/*  31:    */   @Column(name="id_sucursal", nullable=false)
/*  32:    */   @NotNull
/*  33:    */   private int idSucursal;
/*  34:    */   @Column(name="id_organizacion", nullable=false)
/*  35:    */   @NotNull
/*  36:    */   private int idOrganizacion;
/*  37:    */   @Column(name="codigo", nullable=false, length=10)
/*  38:    */   @NotNull
/*  39:    */   @Size(min=2, max=10)
/*  40:    */   private String codigo;
/*  41:    */   @Column(name="nombre", nullable=false, length=50)
/*  42:    */   @Size(min=1, max=50)
/*  43:    */   @NotNull
/*  44:    */   private String nombre;
/*  45:    */   @Column(name="descripcion", nullable=true, length=200)
/*  46:    */   @Size(max=200)
/*  47:    */   private String descripcion;
/*  48:    */   @Column(name="estado", nullable=false)
/*  49:    */   @NotNull
/*  50: 72 */   private boolean estado = true;
/*  51:    */   @Column(name="predeterminado", nullable=false)
/*  52:    */   private boolean predeterminado;
/*  53:    */   @ManyToMany(fetch=FetchType.LAZY)
/*  54:    */   @JoinTable(name="impuesto_categoria", joinColumns={@javax.persistence.JoinColumn(name="id_categoria_impuesto")}, inverseJoinColumns={@javax.persistence.JoinColumn(name="id_impuesto")})
/*  55: 79 */   private List<Impuesto> listaImpuesto = new ArrayList();
/*  56:    */   
/*  57:    */   public CategoriaImpuesto() {}
/*  58:    */   
/*  59:    */   public CategoriaImpuesto(int idCategoriaImpuesto, String codigo, String nombre)
/*  60:    */   {
/*  61: 92 */     this.idCategoriaImpuesto = idCategoriaImpuesto;
/*  62: 93 */     this.codigo = codigo;
/*  63: 94 */     this.nombre = nombre;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int getId()
/*  67:    */   {
/*  68:104 */     return this.idCategoriaImpuesto;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int getIdCategoriaImpuesto()
/*  72:    */   {
/*  73:108 */     return this.idCategoriaImpuesto;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setIdCategoriaImpuesto(int idCategoriaImpuesto)
/*  77:    */   {
/*  78:112 */     this.idCategoriaImpuesto = idCategoriaImpuesto;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public int getIdSucursal()
/*  82:    */   {
/*  83:116 */     return this.idSucursal;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setIdSucursal(int idSucursal)
/*  87:    */   {
/*  88:120 */     this.idSucursal = idSucursal;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public int getIdOrganizacion()
/*  92:    */   {
/*  93:124 */     return this.idOrganizacion;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setIdOrganizacion(int idOrganizacion)
/*  97:    */   {
/*  98:128 */     this.idOrganizacion = idOrganizacion;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String getNombre()
/* 102:    */   {
/* 103:132 */     return this.nombre;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setNombre(String nombre)
/* 107:    */   {
/* 108:136 */     this.nombre = nombre;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String getDescripcion()
/* 112:    */   {
/* 113:140 */     return this.descripcion;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setDescripcion(String descripcion)
/* 117:    */   {
/* 118:144 */     this.descripcion = descripcion;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public boolean isEstado()
/* 122:    */   {
/* 123:148 */     return this.estado;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setEstado(boolean estado)
/* 127:    */   {
/* 128:152 */     this.estado = estado;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public List<Impuesto> getListaImpuesto()
/* 132:    */   {
/* 133:156 */     return this.listaImpuesto;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setListaImpuesto(List<Impuesto> listaImpuesto)
/* 137:    */   {
/* 138:160 */     this.listaImpuesto = listaImpuesto;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public String getCodigo()
/* 142:    */   {
/* 143:164 */     return this.codigo;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setCodigo(String codigo)
/* 147:    */   {
/* 148:168 */     this.codigo = codigo;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public boolean isPredeterminado()
/* 152:    */   {
/* 153:172 */     return this.predeterminado;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setPredeterminado(boolean predeterminado)
/* 157:    */   {
/* 158:176 */     this.predeterminado = predeterminado;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public String toString()
/* 162:    */   {
/* 163:181 */     return this.nombre;
/* 164:    */   }
/* 165:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.CategoriaImpuesto
 * JD-Core Version:    0.7.0.1
 */