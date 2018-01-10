/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.Entity;
/*   5:    */ import javax.persistence.FetchType;
/*   6:    */ import javax.persistence.GeneratedValue;
/*   7:    */ import javax.persistence.GenerationType;
/*   8:    */ import javax.persistence.Id;
/*   9:    */ import javax.persistence.JoinColumn;
/*  10:    */ import javax.persistence.ManyToOne;
/*  11:    */ import javax.persistence.Table;
/*  12:    */ import javax.persistence.TableGenerator;
/*  13:    */ import javax.validation.constraints.NotNull;
/*  14:    */ import javax.validation.constraints.Size;
/*  15:    */ 
/*  16:    */ @Entity
/*  17:    */ @Table(name="subempresa", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "id_empresa_padre", "codigo"})})
/*  18:    */ public class Subempresa
/*  19:    */   extends EntidadBase
/*  20:    */ {
/*  21:    */   private static final long serialVersionUID = 1L;
/*  22:    */   @Id
/*  23:    */   @TableGenerator(name="subempresa", initialValue=0, allocationSize=50)
/*  24:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="subempresa")
/*  25:    */   @Column(name="id_subempresa")
/*  26:    */   private int idSubempresa;
/*  27:    */   @Column(name="id_organizacion", nullable=false)
/*  28:    */   private int idOrganizacion;
/*  29:    */   @Column(name="id_sucursal", nullable=false)
/*  30:    */   private int idSucursal;
/*  31:    */   @Column(name="empresa_final", nullable=false)
/*  32:    */   @Size(min=2, max=200)
/*  33:    */   private String empresaFinal;
/*  34:    */   @Column(name="codigo", nullable=false)
/*  35:    */   @Size(min=1, max=20)
/*  36:    */   private String codigo;
/*  37:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  38:    */   @JoinColumn(name="id_empresa_padre", nullable=true)
/*  39:    */   private Empresa empresaPadre;
/*  40:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  41:    */   @JoinColumn(name="id_empresa", nullable=false)
/*  42:    */   @NotNull
/*  43:    */   private Empresa empresa;
/*  44:    */   @Column(name="activo", nullable=false)
/*  45: 73 */   private boolean activo = true;
/*  46:    */   
/*  47:    */   public int getIdSubempresa()
/*  48:    */   {
/*  49: 94 */     return this.idSubempresa;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void setIdSubempresa(int idSubempresa)
/*  53:    */   {
/*  54:104 */     this.idSubempresa = idSubempresa;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public int getIdOrganizacion()
/*  58:    */   {
/*  59:113 */     return this.idOrganizacion;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void setIdOrganizacion(int idOrganizacion)
/*  63:    */   {
/*  64:123 */     this.idOrganizacion = idOrganizacion;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public int getIdSucursal()
/*  68:    */   {
/*  69:132 */     return this.idSucursal;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setIdSucursal(int idSucursal)
/*  73:    */   {
/*  74:142 */     this.idSucursal = idSucursal;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String getEmpresaFinal()
/*  78:    */   {
/*  79:151 */     return this.empresaFinal;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setEmpresaFinal(String empresaFinal)
/*  83:    */   {
/*  84:161 */     this.empresaFinal = empresaFinal;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public Empresa getEmpresaPadre()
/*  88:    */   {
/*  89:170 */     return this.empresaPadre;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setEmpresaPadre(Empresa empresaPadre)
/*  93:    */   {
/*  94:180 */     this.empresaPadre = empresaPadre;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public Empresa getEmpresa()
/*  98:    */   {
/*  99:189 */     return this.empresa;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setEmpresa(Empresa empresa)
/* 103:    */   {
/* 104:199 */     this.empresa = empresa;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String getCodigo()
/* 108:    */   {
/* 109:208 */     return this.codigo;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setCodigo(String codigo)
/* 113:    */   {
/* 114:218 */     this.codigo = codigo;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public int getId()
/* 118:    */   {
/* 119:228 */     return this.idSubempresa;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public boolean isActivo()
/* 123:    */   {
/* 124:232 */     return this.activo;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setActivo(boolean activo)
/* 128:    */   {
/* 129:236 */     this.activo = activo;
/* 130:    */   }
/* 131:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Subempresa
 * JD-Core Version:    0.7.0.1
 */