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
/*  15:    */ @Table(name="origen_ingresos", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  16:    */ public class OrigenIngresos
/*  17:    */   extends EntidadBase
/*  18:    */   implements Serializable
/*  19:    */ {
/*  20:    */   private static final long serialVersionUID = 1L;
/*  21:    */   @Id
/*  22:    */   @TableGenerator(name="origen_ingresos", initialValue=0, allocationSize=50)
/*  23:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="origen_ingresos")
/*  24:    */   @Column(name="id_origen_ingresos")
/*  25:    */   private int idOrigenIngresos;
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
/*  36:    */   @Size(min=2, max=50)
/*  37:    */   private String nombre;
/*  38:    */   @Column(name="descripcion", length=200)
/*  39:    */   @Size(max=200)
/*  40:    */   private String descripcion;
/*  41:    */   @Column(name="activo", nullable=false)
/*  42:    */   private boolean activo;
/*  43:    */   @Column(name="predeterminado", nullable=false)
/*  44:    */   private boolean predeterminado;
/*  45:    */   
/*  46:    */   public OrigenIngresos() {}
/*  47:    */   
/*  48:    */   public OrigenIngresos(int idOrigenIngresos, String codigo, String nombre)
/*  49:    */   {
/*  50: 68 */     this.idOrigenIngresos = idOrigenIngresos;
/*  51: 69 */     this.codigo = codigo;
/*  52: 70 */     this.nombre = nombre;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public int getId()
/*  56:    */   {
/*  57: 75 */     return this.idOrigenIngresos;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int getIdOrganizacion()
/*  61:    */   {
/*  62: 84 */     return this.idOrganizacion;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setIdOrganizacion(int idOrganizacion)
/*  66:    */   {
/*  67: 94 */     this.idOrganizacion = idOrganizacion;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int getIdSucursal()
/*  71:    */   {
/*  72:103 */     return this.idSucursal;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setIdSucursal(int idSucursal)
/*  76:    */   {
/*  77:113 */     this.idSucursal = idSucursal;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String getCodigo()
/*  81:    */   {
/*  82:122 */     return this.codigo;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setCodigo(String codigo)
/*  86:    */   {
/*  87:132 */     this.codigo = codigo;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String getNombre()
/*  91:    */   {
/*  92:141 */     return this.nombre;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setNombre(String nombre)
/*  96:    */   {
/*  97:151 */     this.nombre = nombre;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String getDescripcion()
/* 101:    */   {
/* 102:160 */     return this.descripcion;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setDescripcion(String descripcion)
/* 106:    */   {
/* 107:170 */     this.descripcion = descripcion;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public boolean isActivo()
/* 111:    */   {
/* 112:179 */     return this.activo;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setActivo(boolean activo)
/* 116:    */   {
/* 117:189 */     this.activo = activo;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public boolean isPredeterminado()
/* 121:    */   {
/* 122:198 */     return this.predeterminado;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setPredeterminado(boolean predeterminado)
/* 126:    */   {
/* 127:208 */     this.predeterminado = predeterminado;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public int getIdOrigenIngresos()
/* 131:    */   {
/* 132:213 */     return this.idOrigenIngresos;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setIdOrigenIngresos(int idOrigenIngresos)
/* 136:    */   {
/* 137:217 */     this.idOrigenIngresos = idOrigenIngresos;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public String toString()
/* 141:    */   {
/* 142:227 */     return this.nombre;
/* 143:    */   }
/* 144:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.OrigenIngresos
 * JD-Core Version:    0.7.0.1
 */