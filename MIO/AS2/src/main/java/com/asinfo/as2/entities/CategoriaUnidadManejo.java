/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.GeneratedValue;
/*   7:    */ import javax.persistence.GenerationType;
/*   8:    */ import javax.persistence.Id;
/*   9:    */ import javax.persistence.Table;
/*  10:    */ import javax.persistence.TableGenerator;
/*  11:    */ import javax.validation.constraints.NotNull;
/*  12:    */ import javax.validation.constraints.Size;
/*  13:    */ 
/*  14:    */ @Entity
/*  15:    */ @Table(name="categoria_unidad_manejo", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  16:    */ public class CategoriaUnidadManejo
/*  17:    */   extends EntidadBase
/*  18:    */   implements Serializable
/*  19:    */ {
/*  20:    */   private static final long serialVersionUID = 1L;
/*  21:    */   @Id
/*  22:    */   @TableGenerator(name="categoria_unidad_manejo", initialValue=0, allocationSize=50)
/*  23:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="categoria_unidad_manejo")
/*  24:    */   @Column(name="id_categoria_unidad_manejo", unique=true, nullable=false)
/*  25:    */   private int idCategoriaUnidadManejo;
/*  26:    */   @Column(name="id_organizacion", nullable=false)
/*  27:    */   private int idOrganizacion;
/*  28:    */   @Column(name="id_sucursal", nullable=false)
/*  29:    */   private int idSucursal;
/*  30:    */   @Column(name="codigo", nullable=false, length=10)
/*  31:    */   @NotNull
/*  32:    */   @Size(min=1, max=10)
/*  33:    */   private String codigo;
/*  34:    */   @Column(name="nombre", nullable=false, length=50)
/*  35:    */   @NotNull
/*  36:    */   @Size(min=1, max=50)
/*  37:    */   private String nombre;
/*  38:    */   @Column(name="descripcion", nullable=true, length=300)
/*  39:    */   @Size(max=300)
/*  40:    */   private String descripcion;
/*  41:    */   @Column(name="activo", nullable=false)
/*  42:    */   @NotNull
/*  43: 62 */   private boolean activo = true;
/*  44:    */   @Column(name="predeterminado", nullable=false)
/*  45:    */   @NotNull
/*  46:    */   private boolean predeterminado;
/*  47:    */   @Column(name="indicador_pallet", nullable=false)
/*  48:    */   @NotNull
/*  49:    */   private boolean indicadorPallet;
/*  50:    */   
/*  51:    */   public CategoriaUnidadManejo() {}
/*  52:    */   
/*  53:    */   public CategoriaUnidadManejo(int idCategoriaUnidadManejo, String codigo, String nombre)
/*  54:    */   {
/*  55: 84 */     this.idCategoriaUnidadManejo = idCategoriaUnidadManejo;
/*  56: 85 */     this.codigo = codigo;
/*  57: 86 */     this.nombre = nombre;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int getId()
/*  61:    */   {
/*  62: 91 */     return this.idCategoriaUnidadManejo;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int getIdCategoriaUnidadManejo()
/*  66:    */   {
/*  67:100 */     return this.idCategoriaUnidadManejo;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setIdCategoriaUnidadManejo(int idCategoriaUnidadManejo)
/*  71:    */   {
/*  72:110 */     this.idCategoriaUnidadManejo = idCategoriaUnidadManejo;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int getIdOrganizacion()
/*  76:    */   {
/*  77:119 */     return this.idOrganizacion;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setIdOrganizacion(int idOrganizacion)
/*  81:    */   {
/*  82:129 */     this.idOrganizacion = idOrganizacion;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public int getIdSucursal()
/*  86:    */   {
/*  87:138 */     return this.idSucursal;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setIdSucursal(int idSucursal)
/*  91:    */   {
/*  92:148 */     this.idSucursal = idSucursal;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String getCodigo()
/*  96:    */   {
/*  97:157 */     return this.codigo;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setCodigo(String codigo)
/* 101:    */   {
/* 102:167 */     this.codigo = codigo;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String getNombre()
/* 106:    */   {
/* 107:176 */     return this.nombre;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setNombre(String nombre)
/* 111:    */   {
/* 112:186 */     this.nombre = nombre;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public String getDescripcion()
/* 116:    */   {
/* 117:195 */     return this.descripcion;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setDescripcion(String descripcion)
/* 121:    */   {
/* 122:205 */     this.descripcion = descripcion;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public boolean isActivo()
/* 126:    */   {
/* 127:214 */     return this.activo;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setActivo(boolean activo)
/* 131:    */   {
/* 132:224 */     this.activo = activo;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public boolean isPredeterminado()
/* 136:    */   {
/* 137:233 */     return this.predeterminado;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setPredeterminado(boolean predeterminado)
/* 141:    */   {
/* 142:243 */     this.predeterminado = predeterminado;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public boolean isIndicadorPallet()
/* 146:    */   {
/* 147:247 */     return this.indicadorPallet;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setIndicadorPallet(boolean indicadorPallet)
/* 151:    */   {
/* 152:251 */     this.indicadorPallet = indicadorPallet;
/* 153:    */   }
/* 154:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.CategoriaUnidadManejo
 * JD-Core Version:    0.7.0.1
 */