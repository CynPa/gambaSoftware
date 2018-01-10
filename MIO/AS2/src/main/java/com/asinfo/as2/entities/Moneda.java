/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.Entity;
/*   5:    */ import javax.persistence.GeneratedValue;
/*   6:    */ import javax.persistence.GenerationType;
/*   7:    */ import javax.persistence.Id;
/*   8:    */ import javax.persistence.Table;
/*   9:    */ import javax.persistence.TableGenerator;
/*  10:    */ import javax.validation.constraints.NotNull;
/*  11:    */ import javax.validation.constraints.Size;
/*  12:    */ 
/*  13:    */ @Entity
/*  14:    */ @Table(name="moneda", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"codigo", "id_organizacion"})})
/*  15:    */ public class Moneda
/*  16:    */   extends EntidadBase
/*  17:    */ {
/*  18:    */   private static final long serialVersionUID = 1L;
/*  19:    */   @Id
/*  20:    */   @TableGenerator(name="moneda", initialValue=0, allocationSize=50)
/*  21:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="moneda")
/*  22:    */   @Column(name="id_moneda")
/*  23:    */   private int idMoneda;
/*  24:    */   @Column(name="id_organizacion", nullable=false)
/*  25:    */   private int idOrganizacion;
/*  26:    */   @Column(name="id_sucursal", nullable=false)
/*  27:    */   private int idSucursal;
/*  28:    */   @Column(name="codigo", length=10, nullable=false)
/*  29:    */   @NotNull
/*  30:    */   @Size(max=10)
/*  31:    */   private String codigo;
/*  32:    */   @Column(name="nombre", length=50, nullable=false)
/*  33:    */   @NotNull
/*  34:    */   @Size(max=50)
/*  35:    */   private String nombre;
/*  36:    */   @Column(name="descripcion", length=200)
/*  37:    */   @Size(max=200)
/*  38:    */   private String descripcion;
/*  39:    */   @Column(name="activo", nullable=false)
/*  40:    */   @NotNull
/*  41:    */   private boolean activo;
/*  42:    */   @Column(name="predeterminado", nullable=false)
/*  43:    */   @NotNull
/*  44:    */   private boolean predeterminado;
/*  45:    */   
/*  46:    */   public Moneda() {}
/*  47:    */   
/*  48:    */   public Moneda(int idMoneda, String codigo, String nombre)
/*  49:    */   {
/*  50: 80 */     this.idMoneda = idMoneda;
/*  51: 81 */     this.codigo = codigo;
/*  52: 82 */     this.nombre = nombre;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public int getId()
/*  56:    */   {
/*  57: 88 */     return this.idMoneda;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int getIdMoneda()
/*  61:    */   {
/*  62: 97 */     return this.idMoneda;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setIdMoneda(int idMoneda)
/*  66:    */   {
/*  67:107 */     this.idMoneda = idMoneda;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int getIdOrganizacion()
/*  71:    */   {
/*  72:116 */     return this.idOrganizacion;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setIdOrganizacion(int idOrganizacion)
/*  76:    */   {
/*  77:126 */     this.idOrganizacion = idOrganizacion;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int getIdSucursal()
/*  81:    */   {
/*  82:135 */     return this.idSucursal;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setIdSucursal(int idSucursal)
/*  86:    */   {
/*  87:145 */     this.idSucursal = idSucursal;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String getCodigo()
/*  91:    */   {
/*  92:154 */     return this.codigo;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setCodigo(String codigo)
/*  96:    */   {
/*  97:164 */     this.codigo = codigo;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String getNombre()
/* 101:    */   {
/* 102:173 */     return this.nombre;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setNombre(String nombre)
/* 106:    */   {
/* 107:183 */     this.nombre = nombre;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String getDescripcion()
/* 111:    */   {
/* 112:192 */     return this.descripcion;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setDescripcion(String descripcion)
/* 116:    */   {
/* 117:202 */     this.descripcion = descripcion;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public boolean isActivo()
/* 121:    */   {
/* 122:211 */     return this.activo;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setActivo(boolean activo)
/* 126:    */   {
/* 127:221 */     this.activo = activo;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public boolean isPredeterminado()
/* 131:    */   {
/* 132:230 */     return this.predeterminado;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setPredeterminado(boolean predeterminado)
/* 136:    */   {
/* 137:240 */     this.predeterminado = predeterminado;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public static long getSerialversionuid()
/* 141:    */   {
/* 142:249 */     return 1L;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public String toString()
/* 146:    */   {
/* 147:254 */     return this.nombre;
/* 148:    */   }
/* 149:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Moneda
 * JD-Core Version:    0.7.0.1
 */